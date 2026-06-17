package maquinavirtualz80;

public class CPU {
    public Memory memory;
    public Registers registers;
    public Stack stack;
    
    public Memory getMemory() {
        return memory;
    }

    public Registers getRegisters() {
        return registers;
    }

    public boolean halted = false;

    public CPU() {
        memory = new Memory();
        registers = new Registers();
        stack = new Stack(memory, registers);
        halted = false;
        
        registers.SP = 0xFFFE;
    }
    
    public void run() {
        while (!halted) {
            step();
        }
    }
    
    public void step() {
        if (halted) return;

        int opcode = memory.read(registers.PC);
        registers.PC++;

        System.out.println("PC: " + registers.PC);
        System.out.println("Executando opcode: " + Integer.toHexString(opcode));

        execute(opcode);
    }
    
    private void execute(int opcode) {
        switch (opcode) {
            // ====================================================
            // CONTROLE DE EXECUÇÃO
            // ====================================================
            case 0x00: // NOP
                break;
                
            case 0x76: // HALT
                halted = true;
                break;

            // ====================================================
            // TRANSFERÊNCIA DE DADOS - LD r, n (Imediato)
            // ====================================================
            case 0x3E: // LD A, n
                int value = memory.read(registers.PC);
                registers.PC++;
                registers.A = value;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x06: // LD B, n
                int valueB = memory.read(registers.PC);
                registers.PC++;
                registers.B = valueB;
                registers.flags.setZero(registers.B == 0);
                registers.flags.setSign((registers.B & 0x80) != 0);
                break;
                
            case 0x0E: // LD C, n
                int valueC = memory.read(registers.PC);
                registers.PC++;
                registers.C = valueC;
                registers.flags.setZero(registers.C == 0);
                registers.flags.setSign((registers.C & 0x80) != 0);
                break;
                
            case 0x16: // LD D, n
                int valueD = memory.read(registers.PC);
                registers.PC++;
                registers.D = valueD;
                registers.flags.setZero(registers.D == 0);
                registers.flags.setSign((registers.D & 0x80) != 0);
                break;
                
            case 0x1E: // LD E, n
                int valueE = memory.read(registers.PC);
                registers.PC++;
                registers.E = valueE;
                registers.flags.setZero(registers.E == 0);
                registers.flags.setSign((registers.E & 0x80) != 0);
                break;
                
            case 0x26: // LD H, n
                int valueH = memory.read(registers.PC);
                registers.PC++;
                registers.H = valueH;
                registers.flags.setZero(registers.H == 0);
                registers.flags.setSign((registers.H & 0x80) != 0);
                break;
                
            case 0x2E: // LD L, n
                int valueL = memory.read(registers.PC);
                registers.PC++;
                registers.L = valueL;
                registers.flags.setZero(registers.L == 0);
                registers.flags.setSign((registers.L & 0x80) != 0);
                break;

            // ====================================================
            // TRANSFERÊNCIA - LD r, r' (Registrador para Registrador)
            // ====================================================
            case 0x7F: // LD A, A (NOP)
                break;
                
            case 0x78: // LD A, B
                registers.A = registers.B;
                break;
                
            case 0x79: // LD A, C
                registers.A = registers.C;
                break;
                
            case 0x7A: // LD A, D
                registers.A = registers.D;
                break;
                
            case 0x7B: // LD A, E
                registers.A = registers.E;
                break;
                
            case 0x7C: // LD A, H
                registers.A = registers.H;
                break;
                
            case 0x7D: // LD A, L
                registers.A = registers.L;
                break;
                
            case 0x40: // LD B, B (NOP)
                break;
                
            case 0x41: // LD B, C
                registers.B = registers.C;
                break;
                
            case 0x42: // LD B, D
                registers.B = registers.D;
                break;
                
            case 0x43: // LD B, E
                registers.B = registers.E;
                break;
                
            case 0x44: // LD B, H
                registers.B = registers.H;
                break;
                
            case 0x45: // LD B, L
                registers.B = registers.L;
                break;
                
            case 0x48: // LD C, B
                registers.C = registers.B;
                break;
                
            case 0x49: // LD C, C (NOP)
                break;
                
            case 0x4A: // LD C, D
                registers.C = registers.D;
                break;
                
            case 0x4B: // LD C, E
                registers.C = registers.E;
                break;
                
            case 0x4C: // LD C, H
                registers.C = registers.H;
                break;
                
            case 0x4D: // LD C, L
                registers.C = registers.L;
                break;
                
            case 0x50: // LD D, B
                registers.D = registers.B;
                break;
                
            case 0x51: // LD D, C
                registers.D = registers.C;
                break;
                
            case 0x52: // LD D, D (NOP)
                break;
                
            case 0x53: // LD D, E
                registers.D = registers.E;
                break;
                
            case 0x54: // LD D, H
                registers.D = registers.H;
                break;
                
            case 0x55: // LD D, L
                registers.D = registers.L;
                break;
                
            case 0x58: // LD E, B
                registers.E = registers.B;
                break;
                
            case 0x59: // LD E, C
                registers.E = registers.C;
                break;
                
            case 0x5A: // LD E, D
                registers.E = registers.D;
                break;
                
            case 0x5B: // LD E, E (NOP)
                break;
                
            case 0x5C: // LD E, H
                registers.E = registers.H;
                break;
                
            case 0x5D: // LD E, L
                registers.E = registers.L;
                break;
                
            case 0x60: // LD H, B
                registers.H = registers.B;
                break;
                
            case 0x61: // LD H, C
                registers.H = registers.C;
                break;
                
            case 0x62: // LD H, D
                registers.H = registers.D;
                break;
                
            case 0x63: // LD H, E
                registers.H = registers.E;
                break;
                
            case 0x64: // LD H, H (NOP)
                break;
                
            case 0x65: // LD H, L
                registers.H = registers.L;
                break;
                
            case 0x68: // LD L, B
                registers.L = registers.B;
                break;
                
            case 0x69: // LD L, C
                registers.L = registers.C;
                break;
                
            case 0x6A: // LD L, D
                registers.L = registers.D;
                break;
                
            case 0x6B: // LD L, E
                registers.L = registers.E;
                break;
                
            case 0x6C: // LD L, H
                registers.L = registers.H;
                break;
                
            case 0x6D: // LD L, L (NOP)
                break;

            // ====================================================
            // TRANSFERÊNCIA - LD r, (HL) - Indireto via HL
            // ====================================================
            case 0x7E: // LD A, (HL)
                registers.A = memory.read(registers.getHL());
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x46: // LD B, (HL)
                registers.B = memory.read(registers.getHL());
                break;
                
            case 0x4E: // LD C, (HL)
                registers.C = memory.read(registers.getHL());
                break;
                
            case 0x56: // LD D, (HL)
                registers.D = memory.read(registers.getHL());
                break;
                
            case 0x5E: // LD E, (HL)
                registers.E = memory.read(registers.getHL());
                break;
                
            case 0x66: // LD H, (HL)
                registers.H = memory.read(registers.getHL());
                break;
                
            case 0x6E: // LD L, (HL)
                registers.L = memory.read(registers.getHL());
                break;

            // ====================================================
            // TRANSFERÊNCIA - LD (HL), r - Indireto via HL
            // ====================================================
            case 0x77: // LD (HL), A
                memory.write(registers.getHL(), registers.A);
                break;
                
            case 0x70: // LD (HL), B
                memory.write(registers.getHL(), registers.B);
                break;
                
            case 0x71: // LD (HL), C
                memory.write(registers.getHL(), registers.C);
                break;
                
            case 0x72: // LD (HL), D
                memory.write(registers.getHL(), registers.D);
                break;
                
            case 0x73: // LD (HL), E
                memory.write(registers.getHL(), registers.E);
                break;
                
            case 0x74: // LD (HL), H
                memory.write(registers.getHL(), registers.H);
                break;
                
            case 0x75: // LD (HL), L
                memory.write(registers.getHL(), registers.L);
                break;

            // ====================================================
            // INCREMENTO E DECREMENTO
            // ====================================================
            case 0x3C: // INC A
                registers.A = (registers.A + 1) & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x04: // INC B
                registers.B = (registers.B + 1) & 0xFF;
                registers.flags.setZero(registers.B == 0);
                registers.flags.setSign((registers.B & 0x80) != 0);
                break;
                
            case 0x0C: // INC C
                registers.C = (registers.C + 1) & 0xFF;
                registers.flags.setZero(registers.C == 0);
                registers.flags.setSign((registers.C & 0x80) != 0);
                break;
                
            case 0x14: // INC D
                registers.D = (registers.D + 1) & 0xFF;
                registers.flags.setZero(registers.D == 0);
                registers.flags.setSign((registers.D & 0x80) != 0);
                break;
                
            case 0x1C: // INC E
                registers.E = (registers.E + 1) & 0xFF;
                registers.flags.setZero(registers.E == 0);
                registers.flags.setSign((registers.E & 0x80) != 0);
                break;
                
            case 0x24: // INC H
                registers.H = (registers.H + 1) & 0xFF;
                registers.flags.setZero(registers.H == 0);
                registers.flags.setSign((registers.H & 0x80) != 0);
                break;
                
            case 0x2C: // INC L
                registers.L = (registers.L + 1) & 0xFF;
                registers.flags.setZero(registers.L == 0);
                registers.flags.setSign((registers.L & 0x80) != 0);
                break;
                
            case 0x3D: // DEC A
                registers.A = (registers.A - 1) & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x05: // DEC B
                registers.B = (registers.B - 1) & 0xFF;
                registers.flags.setZero(registers.B == 0);
                registers.flags.setSign((registers.B & 0x80) != 0);
                break;
                
            case 0x0D: // DEC C
                registers.C = (registers.C - 1) & 0xFF;
                registers.flags.setZero(registers.C == 0);
                registers.flags.setSign((registers.C & 0x80) != 0);
                break;
                
            case 0x15: // DEC D
                registers.D = (registers.D - 1) & 0xFF;
                registers.flags.setZero(registers.D == 0);
                registers.flags.setSign((registers.D & 0x80) != 0);
                break;
                
            case 0x1D: // DEC E
                registers.E = (registers.E - 1) & 0xFF;
                registers.flags.setZero(registers.E == 0);
                registers.flags.setSign((registers.E & 0x80) != 0);
                break;
                
            case 0x25: // DEC H
                registers.H = (registers.H - 1) & 0xFF;
                registers.flags.setZero(registers.H == 0);
                registers.flags.setSign((registers.H & 0x80) != 0);
                break;
                
            case 0x2D: // DEC L
                registers.L = (registers.L - 1) & 0xFF;
                registers.flags.setZero(registers.L == 0);
                registers.flags.setSign((registers.L & 0x80) != 0);
                break;

            // ====================================================
            // ARITMÉTICAS - ADD A, r
            // ====================================================
            case 0x80: // ADD A, B
                int resultAB = registers.A + registers.B;
                registers.flags.setCarry(resultAB > 0xFF);
                registers.A = resultAB & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x81: // ADD A, C
                int resultAC = registers.A + registers.C;
                registers.flags.setCarry(resultAC > 0xFF);
                registers.A = resultAC & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x82: // ADD A, D
                int resultAD = registers.A + registers.D;
                registers.flags.setCarry(resultAD > 0xFF);
                registers.A = resultAD & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x83: // ADD A, E
                int resultAE = registers.A + registers.E;
                registers.flags.setCarry(resultAE > 0xFF);
                registers.A = resultAE & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x84: // ADD A, H
                int resultAH = registers.A + registers.H;
                registers.flags.setCarry(resultAH > 0xFF);
                registers.A = resultAH & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x85: // ADD A, L
                int resultAL = registers.A + registers.L;
                registers.flags.setCarry(resultAL > 0xFF);
                registers.A = resultAL & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0xC6: // ADD A, n
                int valueAdd = memory.read(registers.PC);
                registers.PC++;
                int result = registers.A + valueAdd;
                registers.flags.setCarry(result > 0xFF);
                registers.A = result & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;

            // ====================================================
            // ARITMÉTICAS - SUB r
            // ====================================================
            case 0x90: // SUB B
                int resultSubB = registers.A - registers.B;
                registers.flags.setCarry(resultSubB < 0);
                registers.A = resultSubB & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x91: // SUB C
                int resultSubC = registers.A - registers.C;
                registers.flags.setCarry(resultSubC < 0);
                registers.A = resultSubC & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x92: // SUB D
                int resultSubD = registers.A - registers.D;
                registers.flags.setCarry(resultSubD < 0);
                registers.A = resultSubD & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x93: // SUB E
                int resultSubE = registers.A - registers.E;
                registers.flags.setCarry(resultSubE < 0);
                registers.A = resultSubE & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x94: // SUB H
                int resultSubH = registers.A - registers.H;
                registers.flags.setCarry(resultSubH < 0);
                registers.A = resultSubH & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0x95: // SUB L
                int resultSubL = registers.A - registers.L;
                registers.flags.setCarry(resultSubL < 0);
                registers.A = resultSubL & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;
                
            case 0xD6: // SUB n
                int valueSub = memory.read(registers.PC);
                registers.PC++;
                int resultSub = registers.A - valueSub;
                registers.flags.setCarry(resultSub < 0);
                registers.A = resultSub & 0xFF;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                break;

            // ====================================================
            // LÓGICAS - AND r
            // ====================================================
            case 0xA0: // AND B
                registers.A = registers.A & registers.B;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA1: // AND C
                registers.A = registers.A & registers.C;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA2: // AND D
                registers.A = registers.A & registers.D;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA3: // AND E
                registers.A = registers.A & registers.E;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA4: // AND H
                registers.A = registers.A & registers.H;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA5: // AND L
                registers.A = registers.A & registers.L;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xE6: // AND n
                int valueAnd = memory.read(registers.PC);
                registers.PC++;
                registers.A = registers.A & valueAnd;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;

            // ====================================================
            // LÓGICAS - OR r
            // ====================================================
            case 0xB0: // OR B
                registers.A = registers.A | registers.B;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xB1: // OR C
                registers.A = registers.A | registers.C;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xB2: // OR D
                registers.A = registers.A | registers.D;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xB3: // OR E
                registers.A = registers.A | registers.E;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xB4: // OR H
                registers.A = registers.A | registers.H;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xB5: // OR L
                registers.A = registers.A | registers.L;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xF6: // OR n
                int valueOr = memory.read(registers.PC);
                registers.PC++;
                registers.A = registers.A | valueOr;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;

            // ====================================================
            // LÓGICAS - XOR r
            // ====================================================
            case 0xA8: // XOR B
                registers.A = registers.A ^ registers.B;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xA9: // XOR C
                registers.A = registers.A ^ registers.C;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xAA: // XOR D
                registers.A = registers.A ^ registers.D;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xAB: // XOR E
                registers.A = registers.A ^ registers.E;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xAC: // XOR H
                registers.A = registers.A ^ registers.H;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xAD: // XOR L
                registers.A = registers.A ^ registers.L;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;
                
            case 0xEE: // XOR n
                int valueXor = memory.read(registers.PC);
                registers.PC++;
                registers.A = registers.A ^ valueXor;
                registers.flags.setZero(registers.A == 0);
                registers.flags.setSign((registers.A & 0x80) != 0);
                registers.flags.setCarry(false);
                break;

            // ====================================================
            // COMPARAÇÃO - CP r
            // ====================================================
            case 0xB8: // CP B
                int resultCpB = registers.A - registers.B;
                registers.flags.setZero((resultCpB & 0xFF) == 0);
                registers.flags.setSign((resultCpB & 0x80) != 0);
                registers.flags.setCarry(resultCpB < 0);
                break;
                
            case 0xB9: // CP C
                int resultCpC = registers.A - registers.C;
                registers.flags.setZero((resultCpC & 0xFF) == 0);
                registers.flags.setSign((resultCpC & 0x80) != 0);
                registers.flags.setCarry(resultCpC < 0);
                break;
                
            case 0xBA: // CP D
                int resultCpD = registers.A - registers.D;
                registers.flags.setZero((resultCpD & 0xFF) == 0);
                registers.flags.setSign((resultCpD & 0x80) != 0);
                registers.flags.setCarry(resultCpD < 0);
                break;
                
            case 0xBB: // CP E
                int resultCpE = registers.A - registers.E;
                registers.flags.setZero((resultCpE & 0xFF) == 0);
                registers.flags.setSign((resultCpE & 0x80) != 0);
                registers.flags.setCarry(resultCpE < 0);
                break;
                
            case 0xBC: // CP H
                int resultCpF = registers.A - registers.H;
                registers.flags.setZero((resultCpF & 0xFF) == 0);
                registers.flags.setSign((resultCpF & 0x80) != 0);
                registers.flags.setCarry(resultCpF < 0);
                break;
                
            case 0xBD: // CP L
                int resultCpG = registers.A - registers.L;
                registers.flags.setZero((resultCpG & 0xFF) == 0);
                registers.flags.setSign((resultCpG & 0x80) != 0);
                registers.flags.setCarry(resultCpG < 0);
                break;
                
            case 0xFE: // CP n
                int valueCp = memory.read(registers.PC);
                registers.PC++;
                int resultCp = registers.A - valueCp;
                registers.flags.setZero((resultCp & 0xFF) == 0);
                registers.flags.setSign((resultCp & 0x80) != 0);
                registers.flags.setCarry(resultCp < 0);
                break;

            // ====================================================
            // CONTROLE DE FLUXO
            // ====================================================
            case 0xC3: // JP nn
                int low = memory.read(registers.PC);
                registers.PC++;
                int high = memory.read(registers.PC);
                registers.PC++;
                int address = (high << 8) | low;
                registers.PC = address;
                break;
                
            case 0x18: // JR e
                int offset = memory.read(registers.PC);
                registers.PC++;
                if (offset > 127) {
                    offset = offset - 256;
                }
                registers.PC = registers.PC + offset;
                break;
                
            case 0xCD: // CALL nn
                int lowCall = memory.read(registers.PC);
                registers.PC++;
                int highCall = memory.read(registers.PC);
                registers.PC++;
                int addressCall = (highCall << 8) | lowCall;
                stack.push16(registers.PC);
                registers.PC = addressCall;
                break;
                
            case 0xC9: // RET
                registers.PC = stack.pop16();
                break;

            // ====================================================
            // PILHA - PUSH
            // ====================================================
            case 0xC5: // PUSH BC
                registers.SP--;
                memory.write(registers.SP, registers.B);
                registers.SP--;
                memory.write(registers.SP, registers.C);
                break;
                
            case 0xD5: // PUSH DE
                registers.SP--;
                memory.write(registers.SP, registers.D);
                registers.SP--;
                memory.write(registers.SP, registers.E);
                break;
                
            case 0xE5: // PUSH HL
                registers.SP--;
                memory.write(registers.SP, registers.H);
                registers.SP--;
                memory.write(registers.SP, registers.L);
                break;

            // ====================================================
            // PILHA - POP
            // ====================================================
            case 0xC1: // POP BC
                registers.C = memory.read(registers.SP);
                registers.SP++;
                registers.B = memory.read(registers.SP);
                registers.SP++;
                break;
                
            case 0xD1: // POP DE
                registers.E = memory.read(registers.SP);
                registers.SP++;
                registers.D = memory.read(registers.SP);
                registers.SP++;
                break;
                
            case 0xE1: // POP HL
                registers.L = memory.read(registers.SP);
                registers.SP++;
                registers.H = memory.read(registers.SP);
                registers.SP++;
                break;

            // ====================================================
            // DEFAULT
            // ====================================================
            default:
                System.out.println("Opcode não implementado: " + Integer.toHexString(opcode));
                halted = true;
                break;
        }
    }
}