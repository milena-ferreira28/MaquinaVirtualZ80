/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class CPU {
    public Memory memory;
    public Registers registers;
    
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
        halted = false;
    }
    //CRIA O RUN
    public void run() {
        while (!halted) {
            step();
        }
    }
    
    //CRIA CICLO STEP
    public void step() {

    if (halted) return;

    // FETCH
    int opcode = memory.read(registers.PC);

    // avança PC
    registers.PC++;

    // DEBUG (opcional, ajuda MUITO)
    System.out.println("Executando opcode: " + Integer.toHexString(opcode));

    // EXECUTE
    execute(opcode);
}
    private void execute(int opcode) {

    switch (opcode) {

        case 0x00: // NOP
            break;
        
        case 0x3E: // LD A, n
            int value = memory.read(registers.PC); // pega o próximo byte
            registers.PC++; // avança o PC
            registers.A = value; // coloca no A
            //ATUALIZA FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0X80) != 0);
            break;
            
        case 0x3C: // INC A
            registers.A = (registers.A + 1) & 0xFF;
            // FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            break;
            
        case 0x76: // HALT
            halted = true;
            break;
        
        case 0x3D: //DEC A
            registers.A = (registers.A - 1) & 0xFF;
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0X80) != 0);
            break;

        default:
            System.out.println("Opcode não implementado: " + Integer.toHexString(opcode));
            halted = true;
            break;
    }
}
    
}
