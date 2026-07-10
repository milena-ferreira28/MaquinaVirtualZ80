package assembler;

import java.io.*;
import java.util.*;

public class Assembler {
    private SymbolTable symbolTable;
    private List<String> lines;
    private List<Integer> codeBytes;
    private List<Integer> relocationTable;
    private int currentAddress;
    private boolean hasErrors;
    private StringBuilder errorLog;
    
    public Assembler() {
    symbolTable = new SymbolTable();
    codeBytes = new ArrayList<>();
    relocationTable = new ArrayList<>();

    hasErrors = false;
    errorLog = new StringBuilder();
}
    
    public boolean assemble(String inputFile, String outputFile) {
        try {
            // Ler arquivo
            lines = readLines(inputFile);
            if (hasErrors) return false;
            
            // Primeira passagem: calcular endereços e coletar rótulos
            firstPass();
            if (hasErrors) return false;
            
            // Segunda passagem: gerar código
            secondPass();
            if (hasErrors) return false;
            
            // Escrever arquivo objeto
            writeObjectFile(outputFile);
            
            // Exibir tabela de símbolos
            symbolTable.print();
            
            return true;
            
        } catch (IOException e) {
            errorLog.append("Erro de I/O: ").append(e.getMessage());
            return false;
        }
    }
    
    private List<String> readLines(String filename) throws IOException {
        List<String> fileLines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int lineNum = 0;
        
        while ((line = reader.readLine()) != null) {
            lineNum++;
            line = line.trim();
            
            // Remove comentários
            int commentIndex = line.indexOf(';');
            if (commentIndex != -1) {
                line = line.substring(0, commentIndex).trim();
            }
            
            if (!line.isEmpty()) {
                fileLines.add(line);
            }
        }
        reader.close();
        return fileLines;
    }
    
    private void firstPass() {
        currentAddress = 0;
        
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Token token = new Tokenizer(line).tokenize();
            
            // Se tem rótulo, adiciona na tabela
            if (token.label != null && !token.label.isEmpty()) {
                if (!symbolTable.addSymbol(token.label, currentAddress)) {
                    error("Linha " + (i+1) + ": Rótulo duplicado: " + token.label);
                    return;
                }
            }
            
            // Se é diretiva ORG
            if (token.opcode != null && token.opcode.equalsIgnoreCase("ORG")) {
                currentAddress = parseValue(token.operand1);
                continue;
            }
            
            // Diretiva PUBLIC

            if (token.opcode != null &&
                token.opcode.equalsIgnoreCase("PUBLIC")) {

                symbolTable.addPublic(token.operand1);
                continue;
            }

            // Diretiva EXTERN

            if (token.opcode != null &&
                token.opcode.equalsIgnoreCase("EXTERN")) {

                symbolTable.addExternal(token.operand1);
                continue;
            }
            
            // Se não tem opcode, pular
            if (token.opcode == null) continue;
            
            // Se é instrução normal
            InstructionInfo info = InstructionTable.get(token.opcode, token.operand1, token.operand2);
            if (info == null) {
                error("Linha " + (i+1) + ": Instrução inválida: " + token.opcode);
                return;
            }
            
            currentAddress += info.size;
        }
    }
    
private void secondPass() {
    currentAddress = 0;
    codeBytes.clear();
    relocationTable.clear();
    
    for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        Token token = new Tokenizer(line).tokenize();
        
        // Processar diretiva ORG
        if (token.opcode != null && token.opcode.equalsIgnoreCase("ORG")) {
            currentAddress = parseValue(token.operand1);
            continue;
        }
        
        // Processar diretiva PUBLIC
        if (token.opcode != null &&
            token.opcode.equalsIgnoreCase("PUBLIC")) {
            continue;
        }

        // Processar diretiva EXTERN
        if (token.opcode != null &&
            token.opcode.equalsIgnoreCase("EXTERN")) {
            continue;
        }
        // Pular linhas sem opcode (só rótulo)
        if (token.opcode == null) continue;
        
        // Gerar bytes da instrução
        InstructionInfo info = InstructionTable.get(token.opcode, token.operand1, token.operand2);
        if (info == null) continue;
        
        // Adicionar opcode
        codeBytes.add(info.opcode);
        
        // Adicionar operandos (resolver rótulos se necessário)
        for (int j = 0; j < info.operandBytes; j++) {
            if (j == 0 && info.hasAddressOperand && token.operand1 != null) {
                // Se é um rótulo, resolve o endereço
                int value = symbolTable.getAddress(token.operand1);
                // Caso seja símbolo externo
                if (value == -1 &&
                    symbolTable.getExternalSymbols().contains(token.operand1)) {


                    // reserva espaço para o linker preencher

                    relocationTable.add(codeBytes.size());


                    codeBytes.add(0);
                    codeBytes.add(0);


                    j++;

                }


                // Caso seja símbolo realmente inexistente
                else if (value == -1 &&
                         token.operand1.matches("[A-Za-z_][A-Za-z0-9_]*")) {


                    error(
                        "Linha " + (i+1)
                        + ": Rótulo não definido: "
                        + token.operand1
                    );

                    return;

                } else if (value != -1) {
                    // É rótulo, usa o valor
                    if (info.operandBytes == 1) {
                        // JR offset
                        int offset = value - (currentAddress + 2);
                        codeBytes.add(offset & 0xFF);
                    } else {
                        // JP, CALL - 2 bytes little-endian guarda onde começa o endereço de 16 bits
                        relocationTable.add(codeBytes.size());

                        codeBytes.add(value & 0xFF);
                        codeBytes.add((value >> 8) & 0xFF);

                        j++;
                    }
                } else {
                    // É valor imediato numérico
                    int val = parseValue(token.operand1);
                    codeBytes.add(val & 0xFF);
                    if (info.operandBytes > 1 && j == 0) {
                        codeBytes.add((val >> 8) & 0xFF);
                        j++;
                    }
                }
            } else if (j < info.operandBytes && token.operand2 != null) {
                // Segundo operando
                if (token.operand2.contains("(HL)")) {
                    // Endereçamento indireto via HL já foi resolvido na tabela
                    // Não precisa adicionar bytes extras
                } else {
                    int val = parseValue(token.operand2);
                    codeBytes.add(val & 0xFF);
                }
            }
        }
        
        currentAddress += info.size;
    }
}
    
    private int parseValue(String operand) {
        if (operand == null) return 0;
        operand = operand.trim();
        try {
            if (operand.startsWith("$")) {
                return Integer.parseInt(operand.substring(1), 16);
            } else if (operand.startsWith("0x") || operand.startsWith("0X")) {
                return Integer.parseInt(operand.substring(2), 16);
            } else {
                return Integer.parseInt(operand);
            }
        } catch (NumberFormatException e) {
            return 0; // Pode ser um rótulo, será resolvido depois
        }
    }
    
    private void writeObjectFile(String filename) throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

    writer.write("SYMBOLS\n");
    for (String s : symbolTable.getSymbols().keySet()) {
        int addr = symbolTable.getAddress(s);
        writer.write(s + " " + addr + "\n");
    }
    writer.write("\n");
    
    writer.write("PUBLIC\n");

    for (String s : symbolTable.getPublicSymbols()) {
        int addr = symbolTable.getAddress(s);
        writer.write(s + " " + addr + "\n");
    }
    writer.write("\n");

    writer.write("EXTERN\n");
    for (String s : symbolTable.getExternalSymbols()) {
        writer.write(s + "\n");
    }
    writer.write("\n");
    
     writer.write("RELOC\n");
    for (Integer pos : relocationTable) {
        writer.write(pos + "\n");
    }
    writer.write("\n");
    
    writer.write("CODE\n");
    for (Integer b : codeBytes) {
        writer.write(String.format("%02X\n", b));
    }
    
    writer.close();
}
    
    private void error(String msg) {
        hasErrors = true;
        errorLog.append(msg).append("\n");
        System.err.println(msg);
    }
    
    public String getErrors() {
        return errorLog.toString();
    }
}