package assembler;

import java.util.*;

public class InstructionTable {
    
    private static class InstEntry {
        String mnemonic;
        int opcode;
        int size;
        boolean hasAddressOperand;
        boolean hasImmediateOperand;
        
        InstEntry(String mnemonic, int opcode, int size, boolean hasAddressOperand) {
            this.mnemonic = mnemonic;
            this.opcode = opcode;
            this.size = size;
            this.hasAddressOperand = hasAddressOperand;
            this.hasImmediateOperand = size > 1 && !hasAddressOperand;
        }
    }
    
    private static Map<String, InstEntry> instructions = new HashMap<>();
    
    static {
        // =============================================
        // CONTROLE DE EXECUÇÃO
        // =============================================
        instructions.put("NOP", new InstEntry("NOP", 0x00, 1, false));
        instructions.put("HALT", new InstEntry("HALT", 0x76, 1, false));
        
        // =============================================
        // TRANSFERÊNCIA - LD r, n (Imediato)
        // =============================================
        instructions.put("LDA", new InstEntry("LDA", 0x3E, 2, false));
        instructions.put("LDB", new InstEntry("LDB", 0x06, 2, false));
        instructions.put("LDC", new InstEntry("LDC", 0x0E, 2, false));
        instructions.put("LDD", new InstEntry("LDD", 0x16, 2, false));
        instructions.put("LDE", new InstEntry("LDE", 0x1E, 2, false));
        instructions.put("LDH", new InstEntry("LDH", 0x26, 2, false));
        instructions.put("LDL", new InstEntry("LDL", 0x2E, 2, false));
        
        // =============================================
        // TRANSFERÊNCIA - LD r, r'
        // =============================================
        instructions.put("LDA_A", new InstEntry("LDA_A", 0x7F, 1, false));
        instructions.put("LDA_B", new InstEntry("LDA_B", 0x78, 1, false));
        instructions.put("LDA_C", new InstEntry("LDA_C", 0x79, 1, false));
        instructions.put("LDA_D", new InstEntry("LDA_D", 0x7A, 1, false));
        instructions.put("LDA_E", new InstEntry("LDA_E", 0x7B, 1, false));
        instructions.put("LDA_H", new InstEntry("LDA_H", 0x7C, 1, false));
        instructions.put("LDA_L", new InstEntry("LDA_L", 0x7D, 1, false));
        
        instructions.put("LDB_A", new InstEntry("LDB_A", 0x47, 1, false));
        instructions.put("LDB_C", new InstEntry("LDB_C", 0x41, 1, false));
        instructions.put("LDB_D", new InstEntry("LDB_D", 0x42, 1, false));
        instructions.put("LDB_E", new InstEntry("LDB_E", 0x43, 1, false));
        instructions.put("LDB_H", new InstEntry("LDB_H", 0x44, 1, false));
        instructions.put("LDB_L", new InstEntry("LDB_L", 0x45, 1, false));
        
        instructions.put("LDC_A", new InstEntry("LDC_A", 0x4F, 1, false));
        instructions.put("LDC_B", new InstEntry("LDC_B", 0x48, 1, false));
        instructions.put("LDC_D", new InstEntry("LDC_D", 0x4A, 1, false));
        instructions.put("LDC_E", new InstEntry("LDC_E", 0x4B, 1, false));
        instructions.put("LDC_H", new InstEntry("LDC_H", 0x4C, 1, false));
        instructions.put("LDC_L", new InstEntry("LDC_L", 0x4D, 1, false));
        
        instructions.put("LDD_A", new InstEntry("LDD_A", 0x57, 1, false));
        instructions.put("LDD_B", new InstEntry("LDD_B", 0x50, 1, false));
        instructions.put("LDD_C", new InstEntry("LDD_C", 0x51, 1, false));
        instructions.put("LDD_E", new InstEntry("LDD_E", 0x53, 1, false));
        instructions.put("LDD_H", new InstEntry("LDD_H", 0x54, 1, false));
        instructions.put("LDD_L", new InstEntry("LDD_L", 0x55, 1, false));
        
        instructions.put("LDE_A", new InstEntry("LDE_A", 0x5F, 1, false));
        instructions.put("LDE_B", new InstEntry("LDE_B", 0x58, 1, false));
        instructions.put("LDE_C", new InstEntry("LDE_C", 0x59, 1, false));
        instructions.put("LDE_D", new InstEntry("LDE_D", 0x5A, 1, false));
        instructions.put("LDE_H", new InstEntry("LDE_H", 0x5C, 1, false));
        instructions.put("LDE_L", new InstEntry("LDE_L", 0x5D, 1, false));
        
        instructions.put("LDH_A", new InstEntry("LDH_A", 0x67, 1, false));
        instructions.put("LDH_B", new InstEntry("LDH_B", 0x60, 1, false));
        instructions.put("LDH_C", new InstEntry("LDH_C", 0x61, 1, false));
        instructions.put("LDH_D", new InstEntry("LDH_D", 0x62, 1, false));
        instructions.put("LDH_E", new InstEntry("LDH_E", 0x63, 1, false));
        instructions.put("LDH_L", new InstEntry("LDH_L", 0x65, 1, false));
        
        instructions.put("LDL_A", new InstEntry("LDL_A", 0x6F, 1, false));
        instructions.put("LDL_B", new InstEntry("LDL_B", 0x68, 1, false));
        instructions.put("LDL_C", new InstEntry("LDL_C", 0x69, 1, false));
        instructions.put("LDL_D", new InstEntry("LDL_D", 0x6A, 1, false));
        instructions.put("LDL_E", new InstEntry("LDL_E", 0x6B, 1, false));
        instructions.put("LDL_H", new InstEntry("LDL_H", 0x6C, 1, false));
        
        // =============================================
        // TRANSFERÊNCIA - LD r, (HL)
        // =============================================
        instructions.put("LDA_HL", new InstEntry("LDA_HL", 0x7E, 1, false));
        instructions.put("LDB_HL", new InstEntry("LDB_HL", 0x46, 1, false));
        instructions.put("LDC_HL", new InstEntry("LDC_HL", 0x4E, 1, false));
        instructions.put("LDD_HL", new InstEntry("LDD_HL", 0x56, 1, false));
        instructions.put("LDE_HL", new InstEntry("LDE_HL", 0x5E, 1, false));
        instructions.put("LDH_HL", new InstEntry("LDH_HL", 0x66, 1, false));
        instructions.put("LDL_HL", new InstEntry("LDL_HL", 0x6E, 1, false));
        
        // =============================================
        // TRANSFERÊNCIA - LD (HL), r
        // =============================================
        instructions.put("LDHL_A", new InstEntry("LDHL_A", 0x77, 1, false));
        instructions.put("LDHL_B", new InstEntry("LDHL_B", 0x70, 1, false));
        instructions.put("LDHL_C", new InstEntry("LDHL_C", 0x71, 1, false));
        instructions.put("LDHL_D", new InstEntry("LDHL_D", 0x72, 1, false));
        instructions.put("LDHL_E", new InstEntry("LDHL_E", 0x73, 1, false));
        instructions.put("LDHL_H", new InstEntry("LDHL_H", 0x74, 1, false));
        instructions.put("LDHL_L", new InstEntry("LDHL_L", 0x75, 1, false));
        
        // =============================================
        // INCREMENTO
        // =============================================
        instructions.put("INCA", new InstEntry("INCA", 0x3C, 1, false));
        instructions.put("INCB", new InstEntry("INCB", 0x04, 1, false));
        instructions.put("INCC", new InstEntry("INCC", 0x0C, 1, false));
        instructions.put("INCD", new InstEntry("INCD", 0x14, 1, false));
        instructions.put("INCE", new InstEntry("INCE", 0x1C, 1, false));
        instructions.put("INCH", new InstEntry("INCH", 0x24, 1, false));
        instructions.put("INCL", new InstEntry("INCL", 0x2C, 1, false));
        
        // =============================================
        // DECREMENTO
        // =============================================
        instructions.put("DECA", new InstEntry("DECA", 0x3D, 1, false));
        instructions.put("DECB", new InstEntry("DECB", 0x05, 1, false));
        instructions.put("DECC", new InstEntry("DECC", 0x0D, 1, false));
        instructions.put("DECD", new InstEntry("DECD", 0x15, 1, false));
        instructions.put("DECE", new InstEntry("DECE", 0x1D, 1, false));
        instructions.put("DECH", new InstEntry("DECH", 0x25, 1, false));
        instructions.put("DECL", new InstEntry("DECL", 0x2D, 1, false));
        
        // =============================================
        // ARITMÉTICAS - ADD A, r
        // =============================================
        instructions.put("ADDA_B", new InstEntry("ADDA_B", 0x80, 1, false));
        instructions.put("ADDA_C", new InstEntry("ADDA_C", 0x81, 1, false));
        instructions.put("ADDA_D", new InstEntry("ADDA_D", 0x82, 1, false));
        instructions.put("ADDA_E", new InstEntry("ADDA_E", 0x83, 1, false));
        instructions.put("ADDA_H", new InstEntry("ADDA_H", 0x84, 1, false));
        instructions.put("ADDA_L", new InstEntry("ADDA_L", 0x85, 1, false));
        instructions.put("ADDA", new InstEntry("ADDA", 0xC6, 2, false)); // ADD A, n
        
        // =============================================
        // ARITMÉTICAS - SUB r
        // =============================================
        instructions.put("SUBA_B", new InstEntry("SUBA_B", 0x90, 1, false));
        instructions.put("SUBA_C", new InstEntry("SUBA_C", 0x91, 1, false));
        instructions.put("SUBA_D", new InstEntry("SUBA_D", 0x92, 1, false));
        instructions.put("SUBA_E", new InstEntry("SUBA_E", 0x93, 1, false));
        instructions.put("SUBA_H", new InstEntry("SUBA_H", 0x94, 1, false));
        instructions.put("SUBA_L", new InstEntry("SUBA_L", 0x95, 1, false));
        instructions.put("SUBA", new InstEntry("SUBA", 0xD6, 2, false)); // SUB n
        
        // =============================================
        // LÓGICAS - AND r
        // =============================================
        instructions.put("ANDA_B", new InstEntry("ANDA_B", 0xA0, 1, false));
        instructions.put("ANDA_C", new InstEntry("ANDA_C", 0xA1, 1, false));
        instructions.put("ANDA_D", new InstEntry("ANDA_D", 0xA2, 1, false));
        instructions.put("ANDA_E", new InstEntry("ANDA_E", 0xA3, 1, false));
        instructions.put("ANDA_H", new InstEntry("ANDA_H", 0xA4, 1, false));
        instructions.put("ANDA_L", new InstEntry("ANDA_L", 0xA5, 1, false));
        instructions.put("ANDA", new InstEntry("ANDA", 0xE6, 2, false)); // AND n
        
        // =============================================
        // LÓGICAS - OR r
        // =============================================
        instructions.put("ORA_B", new InstEntry("ORA_B", 0xB0, 1, false));
        instructions.put("ORA_C", new InstEntry("ORA_C", 0xB1, 1, false));
        instructions.put("ORA_D", new InstEntry("ORA_D", 0xB2, 1, false));
        instructions.put("ORA_E", new InstEntry("ORA_E", 0xB3, 1, false));
        instructions.put("ORA_H", new InstEntry("ORA_H", 0xB4, 1, false));
        instructions.put("ORA_L", new InstEntry("ORA_L", 0xB5, 1, false));
        instructions.put("ORA", new InstEntry("ORA", 0xF6, 2, false)); // OR n
        
        // =============================================
        // LÓGICAS - XOR r
        // =============================================
        instructions.put("XORA_B", new InstEntry("XORA_B", 0xA8, 1, false));
        instructions.put("XORA_C", new InstEntry("XORA_C", 0xA9, 1, false));
        instructions.put("XORA_D", new InstEntry("XORA_D", 0xAA, 1, false));
        instructions.put("XORA_E", new InstEntry("XORA_E", 0xAB, 1, false));
        instructions.put("XORA_H", new InstEntry("XORA_H", 0xAC, 1, false));
        instructions.put("XORA_L", new InstEntry("XORA_L", 0xAD, 1, false));
        instructions.put("XORA", new InstEntry("XORA", 0xEE, 2, false)); // XOR n
        
        // =============================================
        // COMPARAÇÃO - CP r
        // =============================================
        instructions.put("CPA_B", new InstEntry("CPA_B", 0xB8, 1, false));
        instructions.put("CPA_C", new InstEntry("CPA_C", 0xB9, 1, false));
        instructions.put("CPA_D", new InstEntry("CPA_D", 0xBA, 1, false));
        instructions.put("CPA_E", new InstEntry("CPA_E", 0xBB, 1, false));
        instructions.put("CPA_H", new InstEntry("CPA_H", 0xBC, 1, false));
        instructions.put("CPA_L", new InstEntry("CPA_L", 0xBD, 1, false));
        instructions.put("CPA", new InstEntry("CPA", 0xFE, 2, false)); // CP n
        
        // =============================================
        // CONTROLE DE FLUXO
        // =============================================
        instructions.put("JP", new InstEntry("JP", 0xC3, 3, true));
        instructions.put("JR", new InstEntry("JR", 0x18, 2, true));
        instructions.put("CALL", new InstEntry("CALL", 0xCD, 3, true));
        instructions.put("RET", new InstEntry("RET", 0xC9, 1, false));
        
        // =============================================
        // PILHA
        // =============================================
        instructions.put("PUSHBC", new InstEntry("PUSHBC", 0xC5, 1, false));
        instructions.put("PUSHDE", new InstEntry("PUSHDE", 0xD5, 1, false));
        instructions.put("PUSHHL", new InstEntry("PUSHHL", 0xE5, 1, false));
        instructions.put("POPBC", new InstEntry("POPBC", 0xC1, 1, false));
        instructions.put("POPDE", new InstEntry("POPDE", 0xD1, 1, false));
        instructions.put("POPHL", new InstEntry("POPHL", 0xE1, 1, false));
    }
    
    public static InstructionInfo get(String mnemonic, String op1, String op2) {
        String key = mnemonic.toUpperCase();
        
        // =============================================
        // NORMALIZAÇÃO PARA LD
        // =============================================
        if (key.equals("LD")) {
            if (op1 == null) return null;
            
            String dest = op1.toUpperCase();
            String src = op2 != null ? op2.toUpperCase() : "";
            
            // LD A, n
            if (dest.equals("A") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDA";
            }
            // LD B, n
            else if (dest.equals("B") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDB";
            }
            // LD C, n
            else if (dest.equals("C") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDC";
            }
            // LD D, n
            else if (dest.equals("D") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDD";
            }
            // LD E, n
            else if (dest.equals("E") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDE";
            }
            // LD H, n
            else if (dest.equals("H") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDH";
            }
            // LD L, n
            else if (dest.equals("L") && !src.isEmpty() && !src.matches("[ABCDEHL]")) {
                key = "LDL";
            }
            // LD A, B
            else if (dest.equals("A") && src.equals("B")) key = "LDA_B";
            else if (dest.equals("A") && src.equals("C")) key = "LDA_C";
            else if (dest.equals("A") && src.equals("D")) key = "LDA_D";
            else if (dest.equals("A") && src.equals("E")) key = "LDA_E";
            else if (dest.equals("A") && src.equals("H")) key = "LDA_H";
            else if (dest.equals("A") && src.equals("L")) key = "LDA_L";
            // LD A, (HL)
            else if (dest.equals("A") && src.equals("(HL)")) key = "LDA_HL";
            // LD (HL), r
            else if (dest.equals("(HL)") && src.matches("[ABCDEHL]")) {
                key = "LDHL_" + src;
            }
            // LD B, A
            else if (dest.equals("B") && src.equals("A")) key = "LDB_A";
            else if (dest.equals("B") && src.equals("C")) key = "LDB_C";
            else if (dest.equals("B") && src.equals("D")) key = "LDB_D";
            else if (dest.equals("B") && src.equals("E")) key = "LDB_E";
            else if (dest.equals("B") && src.equals("H")) key = "LDB_H";
            else if (dest.equals("B") && src.equals("L")) key = "LDB_L";
            // LD B, (HL)
            else if (dest.equals("B") && src.equals("(HL)")) key = "LDB_HL";
            // LD C, A
            else if (dest.equals("C") && src.equals("A")) key = "LDC_A";
            else if (dest.equals("C") && src.equals("B")) key = "LDC_B";
            else if (dest.equals("C") && src.equals("D")) key = "LDC_D";
            else if (dest.equals("C") && src.equals("E")) key = "LDC_E";
            else if (dest.equals("C") && src.equals("H")) key = "LDC_H";
            else if (dest.equals("C") && src.equals("L")) key = "LDC_L";
            // LD C, (HL)
            else if (dest.equals("C") && src.equals("(HL)")) key = "LDC_HL";
            // LD D, A
            else if (dest.equals("D") && src.equals("A")) key = "LDD_A";
            else if (dest.equals("D") && src.equals("B")) key = "LDD_B";
            else if (dest.equals("D") && src.equals("C")) key = "LDD_C";
            else if (dest.equals("D") && src.equals("E")) key = "LDD_E";
            else if (dest.equals("D") && src.equals("H")) key = "LDD_H";
            else if (dest.equals("D") && src.equals("L")) key = "LDD_L";
            // LD D, (HL)
            else if (dest.equals("D") && src.equals("(HL)")) key = "LDD_HL";
            // LD E, A
            else if (dest.equals("E") && src.equals("A")) key = "LDE_A";
            else if (dest.equals("E") && src.equals("B")) key = "LDE_B";
            else if (dest.equals("E") && src.equals("C")) key = "LDE_C";
            else if (dest.equals("E") && src.equals("D")) key = "LDE_D";
            else if (dest.equals("E") && src.equals("H")) key = "LDE_H";
            else if (dest.equals("E") && src.equals("L")) key = "LDE_L";
            // LD E, (HL)
            else if (dest.equals("E") && src.equals("(HL)")) key = "LDE_HL";
            // LD H, A
            else if (dest.equals("H") && src.equals("A")) key = "LDH_A";
            else if (dest.equals("H") && src.equals("B")) key = "LDH_B";
            else if (dest.equals("H") && src.equals("C")) key = "LDH_C";
            else if (dest.equals("H") && src.equals("D")) key = "LDH_D";
            else if (dest.equals("H") && src.equals("E")) key = "LDH_E";
            else if (dest.equals("H") && src.equals("L")) key = "LDH_L";
            // LD H, (HL)
            else if (dest.equals("H") && src.equals("(HL)")) key = "LDH_HL";
            // LD L, A
            else if (dest.equals("L") && src.equals("A")) key = "LDL_A";
            else if (dest.equals("L") && src.equals("B")) key = "LDL_B";
            else if (dest.equals("L") && src.equals("C")) key = "LDL_C";
            else if (dest.equals("L") && src.equals("D")) key = "LDL_D";
            else if (dest.equals("L") && src.equals("E")) key = "LDL_E";
            else if (dest.equals("L") && src.equals("H")) key = "LDL_H";
            // LD L, (HL)
            else if (dest.equals("L") && src.equals("(HL)")) key = "LDL_HL";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA ADD
        // =============================================
        else if (key.equals("ADD")) {
            if (op1 == null || op2 == null) return null;
            
            String dest = op1.toUpperCase();
            String src = op2.toUpperCase();
            
            if (dest.equals("A")) {
                if (src.equals("B")) key = "ADDA_B";
                else if (src.equals("C")) key = "ADDA_C";
                else if (src.equals("D")) key = "ADDA_D";
                else if (src.equals("E")) key = "ADDA_E";
                else if (src.equals("H")) key = "ADDA_H";
                else if (src.equals("L")) key = "ADDA_L";
                else if (!src.matches("[ABCDEHL]")) key = "ADDA";
            }
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA SUB
        // =============================================
        else if (key.equals("SUB")) {
            if (op1 == null) return null;
            
            String src = op1.toUpperCase();
            
            if (src.equals("B")) key = "SUBA_B";
            else if (src.equals("C")) key = "SUBA_C";
            else if (src.equals("D")) key = "SUBA_D";
            else if (src.equals("E")) key = "SUBA_E";
            else if (src.equals("H")) key = "SUBA_H";
            else if (src.equals("L")) key = "SUBA_L";
            else if (!src.matches("[ABCDEHL]")) key = "SUBA";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA AND
        // =============================================
        else if (key.equals("AND")) {
            if (op1 == null) return null;
            
            String src = op1.toUpperCase();
            
            if (src.equals("B")) key = "ANDA_B";
            else if (src.equals("C")) key = "ANDA_C";
            else if (src.equals("D")) key = "ANDA_D";
            else if (src.equals("E")) key = "ANDA_E";
            else if (src.equals("H")) key = "ANDA_H";
            else if (src.equals("L")) key = "ANDA_L";
            else if (!src.matches("[ABCDEHL]")) key = "ANDA";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA OR
        // =============================================
        else if (key.equals("OR")) {
            if (op1 == null) return null;
            
            String src = op1.toUpperCase();
            
            if (src.equals("B")) key = "ORA_B";
            else if (src.equals("C")) key = "ORA_C";
            else if (src.equals("D")) key = "ORA_D";
            else if (src.equals("E")) key = "ORA_E";
            else if (src.equals("H")) key = "ORA_H";
            else if (src.equals("L")) key = "ORA_L";
            else if (!src.matches("[ABCDEHL]")) key = "ORA";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA XOR
        // =============================================
        else if (key.equals("XOR")) {
            if (op1 == null) return null;
            
            String src = op1.toUpperCase();
            
            if (src.equals("B")) key = "XORA_B";
            else if (src.equals("C")) key = "XORA_C";
            else if (src.equals("D")) key = "XORA_D";
            else if (src.equals("E")) key = "XORA_E";
            else if (src.equals("H")) key = "XORA_H";
            else if (src.equals("L")) key = "XORA_L";
            else if (!src.matches("[ABCDEHL]")) key = "XORA";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA CP
        // =============================================
        else if (key.equals("CP")) {
            if (op1 == null) return null;
            
            String src = op1.toUpperCase();
            
            if (src.equals("B")) key = "CPA_B";
            else if (src.equals("C")) key = "CPA_C";
            else if (src.equals("D")) key = "CPA_D";
            else if (src.equals("E")) key = "CPA_E";
            else if (src.equals("H")) key = "CPA_H";
            else if (src.equals("L")) key = "CPA_L";
            else if (!src.matches("[ABCDEHL]")) key = "CPA";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA INC
        // =============================================
        else if (key.equals("INC")) {
            if (op1 == null) return null;
            
            String reg = op1.toUpperCase();
            
            if (reg.equals("A")) key = "INCA";
            else if (reg.equals("B")) key = "INCB";
            else if (reg.equals("C")) key = "INCC";
            else if (reg.equals("D")) key = "INCD";
            else if (reg.equals("E")) key = "INCE";
            else if (reg.equals("H")) key = "INCH";
            else if (reg.equals("L")) key = "INCL";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA DEC
        // =============================================
        else if (key.equals("DEC")) {
            if (op1 == null) return null;
            
            String reg = op1.toUpperCase();
            
            if (reg.equals("A")) key = "DECA";
            else if (reg.equals("B")) key = "DECB";
            else if (reg.equals("C")) key = "DECC";
            else if (reg.equals("D")) key = "DECD";
            else if (reg.equals("E")) key = "DECE";
            else if (reg.equals("H")) key = "DECH";
            else if (reg.equals("L")) key = "DECL";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA PUSH
        // =============================================
        else if (key.equals("PUSH")) {
            if (op1 == null) return null;
            
            String reg = op1.toUpperCase();
            
            if (reg.equals("BC")) key = "PUSHBC";
            else if (reg.equals("DE")) key = "PUSHDE";
            else if (reg.equals("HL")) key = "PUSHHL";
        }
        
        // =============================================
        // NORMALIZAÇÃO PARA POP
        // =============================================
        else if (key.equals("POP")) {
            if (op1 == null) return null;
            
            String reg = op1.toUpperCase();
            
            if (reg.equals("BC")) key = "POPBC";
            else if (reg.equals("DE")) key = "POPDE";
            else if (reg.equals("HL")) key = "POPHL";
        }
        
        InstEntry entry = instructions.get(key);
        if (entry == null) return null;
        
        return new InstructionInfo(entry.opcode, entry.size, entry.hasAddressOperand);
    }
}