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
        
        registers.SP = 0xFFFE;
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
            
        //====================================================          
        case 0x3E: // LD A, n
            int value = memory.read(registers.PC); // pega o próximo byte
            registers.PC++; // avança o PC
            registers.A = value; // coloca no A
            //ATUALIZA FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0X80) != 0);
            break;
            
        //====================================================              
        case 0x3C: // INC A
            registers.A = (registers.A + 1) & 0xFF;
            // FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            break;
            
         //====================================================             
        case 0x76: // HALT
            halted = true;
            break;
            
        //====================================================          
        case 0x3D: //DEC A
            registers.A = (registers.A - 1) & 0xFF;
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0X80) != 0);
            break;
         
        //====================================================  
        case 0xC6: // ADD A, n
            int valueAdd = memory.read(registers.PC);
            registers.PC++;
            
            int result = registers.A + valueAdd;
            
            //Carry (passou de 255?)
            registers.flags.setCarry(result > 0xFF);
            
            //Mantém só 8 bits
            registers.A = result & 0xFF;
            
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            
            break;

        //====================================================  
        case 0xD6: // SUB A, n
            int valueSub = memory.read(registers.PC);
            registers.PC++;
            
            int resultSub = registers.A - valueSub;
            
            //Carry (em subtração = borrow)
            registers.flags.setCarry(resultSub < 0);
            registers.A = resultSub & 0xFF;
            
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            
            break;
            
        //====================================================  
        case 0xE6: //AND A, n
            int valueAnd = memory.read(registers.PC);
            registers.PC++;
            
            registers.A = registers.A & valueAnd;
            
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            registers.flags.setCarry(false); //AND zera carry
            
            break;
            
        //==================================================== 
        case 0xF6: //OR A, n
            int valueOr = memory.read(registers.PC);
            registers.PC++;
            
            registers.A = registers.A | valueOr;
            
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            registers.flags.setCarry(false); 
            
            break;
            
        //==================================================== 
        case 0xEE: // XOR A, n
            int valueXor = memory.read(registers.PC);
            registers.PC++;
            
            registers.A = registers.A ^ valueXor;
            
            //FLAGS
            registers.flags.setZero(registers.A == 0);
            registers.flags.setSign((registers.A & 0x80) != 0);
            registers.flags.setCarry(false); 
            
            break;
           
        //==================================================== 
        case 0xFE: // CP A, n
            int valueCp = memory.read(registers.PC);
            registers.PC++;
            
            int resultCp = registers.A - valueCp;
            
            //FLAGS
            registers.flags.setZero((resultCp & 0xFF) == 0);
            registers.flags.setSign((resultCp & 0x80) != 0);
            registers.flags.setCarry(resultCp < 0); 
            
            break;
            
        //==================================================== 
        case 0xC3: //JP nn
            int low = memory.read(registers.PC);
            registers.PC++;
            
            int high = memory.read(registers.PC);
            registers.PC++;
            
            int adress = (high << 8) | low;
            
            registers.PC = adress;
            
            break;
            
        //==================================================== 
        case 0x18: //JR e
            int offset = memory.read(registers.PC);
            registers.PC++;
            
            //converte pra signed (byte com sinal)
            if (offset > 127) {
                offset = offset - 256;
            }
            
            registers.PC = registers.PC + offset;
            
            break;
            
        //==================================================== 
        case 0xCD: //CALL nn
            int lowCall = memory.read(registers.PC);
            registers.PC++;
            
            int highCall = memory.read(registers.PC);
            registers.PC++;
            
            int adressCall = (highCall << 8) | lowCall;
            
            //empilha o pc atual
            registers.SP--;
            memory.write(registers.SP, (registers.PC >> 8) & 0xFF); //HIGH
            
            registers.SP--;
            memory.write(registers.SP, registers.PC & 0xFF); //low
            
            //salta
            registers.PC = adressCall;
            
            break;
            
        //==================================================== 
        case 0xC9: // RET
        int lowRet = memory.read(registers.SP);
        registers.SP++;

        int highRet = memory.read(registers.SP);
        registers.SP++;

        int returnAddress = (highRet << 8) | lowRet;

        registers.PC = returnAddress;

        break;
        
        //====================================================
        default:
            System.out.println("Opcode não implementado: " + Integer.toHexString(opcode));
            halted = true;
            break;
    }
    }
    
}
