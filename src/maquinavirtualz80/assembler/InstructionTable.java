package maquinavirtualz80.assembler;

import java.util.*;

public class InstructionTable {
    
    private static class InstEntry {
        String mnemonic;
        int opcode;
        int size;
        boolean hasAddressOperand;
        
        InstEntry(String mnemonic, int opcode, int size, boolean hasAddressOperand) {
            this.mnemonic = mnemonic;
            this.opcode = opcode;
            this.size = size;
            this.hasAddressOperand = hasAddressOperand;
        }
    }
    
    private static Map<String, InstEntry> instructions = new HashMap<>();
    
    static {
        // Transferência / Controle
        instructions.put("NOP", new InstEntry("NOP", 0x00, 1, false));
        instructions.put("HALT", new InstEntry("HALT", 0x76, 1, false));
        
        // LD A, n
        instructions.put("LDA", new InstEntry("LDA", 0x3E, 2, false));
        
        // LD B, n
        instructions.put("LDB", new InstEntry("LDB", 0x06, 2, false));
        
        // INC A
        instructions.put("INCA", new InstEntry("INCA", 0x3C, 1, false));
        
        // DEC A
        instructions.put("DECA", new InstEntry("DECA", 0x3D, 1, false));
        
        // ADD A, n
        instructions.put("ADDA", new InstEntry("ADDA", 0xC6, 2, false));
        
        // ADD A, B
        instructions.put("ADDAB", new InstEntry("ADDAB", 0x80, 1, false));
        
        // SUB A, n
        instructions.put("SUBA", new InstEntry("SUBA", 0xD6, 2, false));
        
        // AND A, n
        instructions.put("ANDA", new InstEntry("ANDA", 0xE6, 2, false));
        
        // OR A, n
        instructions.put("ORA", new InstEntry("ORA", 0xF6, 2, false));
        
        // XOR A, n
        instructions.put("XORA", new InstEntry("XORA", 0xEE, 2, false));
        
        // CP A, n
        instructions.put("CPA", new InstEntry("CPA", 0xFE, 2, false));
        
        // JP nn
        instructions.put("JP", new InstEntry("JP", 0xC3, 3, true));
        
        // JR e
        instructions.put("JR", new InstEntry("JR", 0x18, 2, true));
        
        // CALL nn
        instructions.put("CALL", new InstEntry("CALL", 0xCD, 3, true));
        
        // RET
        instructions.put("RET", new InstEntry("RET", 0xC9, 1, false));
        
        // PUSH BC
        instructions.put("PUSHBC", new InstEntry("PUSHBC", 0xC5, 1, false));
        
        // POP BC
        instructions.put("POPBC", new InstEntry("POPBC", 0xC1, 1, false));
    }
    
    public static InstructionInfo get(String mnemonic, String op1, String op2) {
        // Normaliza mnemônico baseado nos operandos
        String key = mnemonic.toUpperCase();
        
        if (key.equals("LD")) {
            if (op1 != null && op1.toUpperCase().equals("A") && op2 != null && !op2.contains(",")) {
                key = "LDA";
            } else if (op1 != null && op1.toUpperCase().equals("B") && op2 != null) {
                key = "LDB";
            } else if (op1 != null && op1.toUpperCase().equals("A") && op2 != null && op2.toUpperCase().equals("B")) {
                key = "ADDAB";
            }
        } else if (key.equals("ADD")) {
            if (op1 != null && op1.toUpperCase().equals("A") && op2 != null && !op2.contains(",")) {
                key = "ADDA";
            } else if (op1 != null && op1.toUpperCase().equals("A") && op2 != null && op2.toUpperCase().equals("B")) {
                key = "ADDAB";
            }
        } else if (key.equals("SUB")) {
            key = "SUBA";
        } else if (key.equals("AND")) {
            key = "ANDA";
        } else if (key.equals("OR")) {
            key = "ORA";
        } else if (key.equals("XOR")) {
            key = "XORA";
        } else if (key.equals("CP")) {
            key = "CPA";
        } else if (key.equals("INC")) {
            if (op1 != null && op1.toUpperCase().equals("A")) key = "INCA";
        } else if (key.equals("DEC")) {
            if (op1 != null && op1.toUpperCase().equals("A")) key = "DECA";
        } else if (key.equals("PUSH")) {
            if (op1 != null && op1.toUpperCase().equals("BC")) key = "PUSHBC";
        } else if (key.equals("POP")) {
            if (op1 != null && op1.toUpperCase().equals("BC")) key = "POPBC";
        }
        
        InstEntry entry = instructions.get(key);
        if (entry == null) return null;
        
        return new InstructionInfo(entry.opcode, entry.size, entry.hasAddressOperand);
    }
}