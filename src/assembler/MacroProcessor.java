package assembler;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class MacroProcessor {

    private Map<String, MacroDefinition> macroTable;
    private boolean hasErrors;
    private StringBuilder errorLog;

    public MacroProcessor() {
        macroTable = new LinkedHashMap<>();
        hasErrors = false;
        errorLog = new StringBuilder();
    }

    // Ponto de entrada: le o fonte, expande macros e grava o arquivo expandido
    public boolean process(String inputFile, String outputFile) {
        try {
            List<String> source = readFile(inputFile);
            List<String> expanded = processLines(source);

            if (!hasErrors) {
                writeFile(outputFile, expanded);
                printTable();
            }
            return !hasErrors;

        } catch (IOException e) {
            error("Erro de I/O: " + e.getMessage());
            return false;
        }
    }

        // PASSAGEM UNICA

    private List<String> processLines(List<String> lines) {
        List<String> output = new ArrayList<>();
        int i = 0;

        while (i < lines.size()) {
            String line = lines.get(i);
            String content = stripComment(line).trim();

            if (content.isEmpty()) {
                output.add(line);
                i++;
                continue;
            }

            // Definicao de macro: coleta e registra, nao gera saida
            if (isMacroHeader(content)) {
                MacroDefinition macro = collectMacro(lines, i);
                if (macro != null) {
                    macroTable.put(macro.name.toUpperCase(), macro);
                    i = macro.endLine + 1;
                } else {
                    i++;
                }
                continue;
            }

            // Chamada de macro: expande e adiciona linhas geradas
            String callName = extractCallName(content);
            if (callName != null && macroTable.containsKey(callName.toUpperCase())) {
                List<String> args = extractArgs(content);
                List<String> expandedLines = expandMacro(callName.toUpperCase(), args, new HashSet<>());
                output.addAll(expandedLines);
            } else {
                output.add(line);
            }
            i++;
        }

        return output;
    }

    // COLETA DE DEFINICAO DE MACRO
    // Coleta o corpo da macro a partir da linha do cabecalho, respeitando aninhamento
    private MacroDefinition collectMacro(List<String> lines, int start) {
        String header = stripComment(lines.get(start)).trim();
        String[] parts = header.split("\\s+", 3);

        if (parts.length < 2) {
            error("Cabecalho de macro invalido na linha " + (start + 1));
            return null;
        }

        String name = parts[0].endsWith(":")
                ? parts[0].substring(0, parts[0].length() - 1)
                : parts[0];

        List<String> params = new ArrayList<>();
        if (parts.length == 3) {
            for (String p : parts[2].split(",")) {
                String trimmed = p.trim();
                if (!trimmed.isEmpty()) params.add(trimmed);
            }
        }

        MacroDefinition macro = new MacroDefinition(name, params);
        int depth = 1;
        int i = start + 1;

        while (i < lines.size()) {
            String line = lines.get(i);
            String content = stripComment(line).trim();

            if (isMacroHeader(content)) {
                // Definicao aninhada: incrementa profundidade e inclui no corpo
                depth++;
                macro.body.add(line);
            } else if (content.equalsIgnoreCase("ENDM")) {
                depth--;
                if (depth == 0) {
                    macro.endLine = i;
                    return macro;
                }
                // ENDM de macro interna: faz parte do corpo da externa
                macro.body.add(line);
            } else {
                macro.body.add(line);
            }
            i++;
        }

        error("ENDM nao encontrado para macro: " + name);
        return null;
    }

    // EXPANSAO DE MACRO

    // Expande uma macro com os argumentos fornecidos, detectando recursao via callStack
    private List<String> expandMacro(String name, List<String> args, Set<String> callStack) {
        MacroDefinition macro = macroTable.get(name);
        List<String> result = new ArrayList<>();

        if (macro == null) {
            error("Macro nao definida: " + name);
            return result;
        }

        if (callStack.contains(name)) {
            error("Recursao detectada em: " + name);
            return result;
        }

        Set<String> stack = new HashSet<>(callStack);
        stack.add(name);

        List<String> body = substituteParams(macro.body, macro.parameters, args);

        int i = 0;
        while (i < body.size()) {
            String line = body.get(i);
            String content = stripComment(line).trim();

            if (content.isEmpty()) {
                result.add(line);
                i++;
                continue;
            }

            // Definicao aninhada descoberta durante a expansao: registra a macro interna
            if (isMacroHeader(content)) {
                MacroDefinition inner = collectMacro(body, i);
                if (inner != null) {
                    macroTable.put(inner.name.toUpperCase(), inner);
                    i = inner.endLine + 1;
                } else {
                    i++;
                }
                continue;
            }

            // Chamada aninhada: expande recursivamente
            String callName = extractCallName(content);
            if (callName != null && macroTable.containsKey(callName.toUpperCase())) {
                List<String> callArgs = extractArgs(content);
                List<String> expanded = expandMacro(callName.toUpperCase(), callArgs, stack);
                result.addAll(expanded);
            } else {
                result.add(line);
            }
            i++;
        }

        return result;
    }

    // SUBSTITUICAO DE PARAMETROS
    private List<String> substituteParams(List<String> body, List<String> params, List<String> args) {
        List<String> result = new ArrayList<>();
        for (String line : body) {
            String out = line;
            for (int i = 0; i < params.size() && i < args.size(); i++) {
                // Substituicao por palavra inteira para evitar substituicoes parciais
                String pattern = "(?<![A-Za-z0-9_])"
                        + Pattern.quote(params.get(i))
                        + "(?![A-Za-z0-9_])";
                out = out.replaceAll(pattern, args.get(i));
            }
            result.add(out);
        }
        return result;
    }

    // AUXILIARES DE PARSING

    // Verifica se a linha e um cabecalho: NOME MACRO [params]
    private boolean isMacroHeader(String content) {
        String[] parts = content.split("\\s+");
        return parts.length >= 2 && parts[1].equalsIgnoreCase("MACRO");
    }

    // Extrai o nome da instrucao/macro, ignorando label se presente
    private String extractCallName(String content) {
        String[] parts = content.split("\\s+");
        int idx = parts[0].endsWith(":") ? 1 : 0;
        return (idx < parts.length) ? parts[idx] : null;
    }

    // Extrai lista de argumentos de uma chamada de macro
    private List<String> extractArgs(String content) {
        List<String> args = new ArrayList<>();
        String[] parts = content.split("\\s+", 2);

        String rest;
        if (parts[0].endsWith(":") && parts.length > 1) {
            String[] inner = parts[1].trim().split("\\s+", 2);
            rest = (inner.length > 1) ? inner[1].trim() : "";
        } else {
            rest = (parts.length > 1) ? parts[1].trim() : "";
        }

        if (!rest.isEmpty()) {
            for (String arg : rest.split(",")) {
                String a = arg.trim();
                if (!a.isEmpty()) args.add(a);
            }
        }
        return args;
    }

    private String stripComment(String line) {
        int idx = line.indexOf(';');
        return (idx != -1) ? line.substring(0, idx) : line;
    }

    // I/O

    private List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private void writeFile(String filename, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("; Gerado pelo pre-processador de macros - nao editar diretamente");
            writer.newLine();
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // TABELA E ERROS

    public void printTable() {
        System.out.println("\n=== TABELA DE MACROS ===");
        if (macroTable.isEmpty()) {
            System.out.println("(nenhuma macro definida)");
            return;
        }
        for (MacroDefinition m : macroTable.values()) {
            System.out.println(m);
        }
    }

    public String getTableString() {
        StringBuilder sb = new StringBuilder("\n=== TABELA DE MACROS ===\n");
        for (MacroDefinition m : macroTable.values()) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }

    private void error(String msg) {
        hasErrors = true;
        errorLog.append(msg).append("\n");
        System.err.println("[MacroProcessor] " + msg);
    }

    public String getErrors() {
        return errorLog.toString();
    }

    public boolean hasErrors() {
        return hasErrors;
    }
}
