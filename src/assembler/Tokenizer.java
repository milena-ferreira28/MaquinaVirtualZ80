package assembler;

public class Tokenizer {
    private String line;
    
    public Tokenizer(String line) {
        this.line = line;
    }
    
    public Token tokenize() {
        Token token = new Token();
        String[] parts = line.split("\\s+");
        
        int idx = 0;
        
        // Verifica se primeiro token é rótulo (termina com :)
        if (parts[idx].endsWith(":")) {
            token.label = parts[idx].substring(0, parts[idx].length() - 1);
            idx++;
        }
        
        if (idx < parts.length) {
            token.opcode = parts[idx].toUpperCase();
            idx++;
        }
        
        if (idx < parts.length) {
            String operands = parts[idx];
            idx++;
            while (idx < parts.length) {
                operands += " " + parts[idx];
                idx++;
            }
            
            // Separa operandos por vírgula
            String[] ops = operands.split(",");
            if (ops.length > 0) {
                token.operand1 = ops[0].trim();
            }
            if (ops.length > 1) {
                token.operand2 = ops[1].trim();
            }
        }
        
        return token;
    }
}