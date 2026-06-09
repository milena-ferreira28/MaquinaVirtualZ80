package maquinavirtualz80.assembler;

public class InstructionInfo {
    public int opcode;
    public int size;
    public boolean hasAddressOperand;
    public int operandBytes;
    
    public InstructionInfo(int opcode, int size, boolean hasAddressOperand) {
        this.opcode = opcode;
        this.size = size;
        this.hasAddressOperand = hasAddressOperand;
        this.operandBytes = size - 1;
    }
}