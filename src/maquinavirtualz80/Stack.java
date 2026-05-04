/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class Stack {
    
    private Memory memory;
    private Registers registers;
    
    public Stack(Memory memory, Registers registers) {
        this.memory = memory;
        this.registers = registers;
    }
    
    public void push16(int value) {
        
        if (registers.SP < 2) {
            throw new RuntimeException("Stack Overflow");
        }
        
        //HIGH
        registers.SP--;
        memory.write(registers.SP, (value >> 8) & 0xFF);
        
        //LOW
        registers.SP--;
        memory.write(registers.SP, value & 0xFF);
    }
    
    public int pop16() {
        
        if(registers.SP > 0xFFFD) {
            throw new RuntimeException("Stack Underflow");
        }
        
        //LOW
        int low = memory.read(registers.SP);
        registers.SP++;
        
        //HIGH
        int high = memory.read(registers.SP);
        registers.SP++;
        
        return (high << 8 ) | low;
    }
}
