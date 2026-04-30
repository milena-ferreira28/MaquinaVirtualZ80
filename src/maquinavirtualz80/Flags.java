/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class Flags {
    
    private boolean S; // Sign
    private boolean Z; // Zero
    private boolean H; // Half Carry
    private boolean PV; // Parity/Overflow
    private boolean N; // Subtraction
    private boolean C; // Carry
    
    public Flags() {
        reset();
    }
    
    public void reset() {
        S = Z = H = PV = N = C = false;
    }
    
    //SETTERS
    public void setSign(boolean value) { S = value; }
    public void setZero(boolean value) { Z = value; }
    public void setHalfCarry(boolean value) { H = value; }
    public void setParityOverflow(boolean value) { PV = value; }
    public void setSubtraction(boolean value) { N = value; }
    public void setCarry(boolean value) { C = value; }
    
    //GETTERS
    public boolean getSign() { return S; }
    public boolean getZero() { return Z; }
    public boolean getHalfCarry() { return H; }
    public boolean getParityOverflow() { return PV; }
    public boolean getSubtraction() { return N; }
    public boolean getCarry() { return C; }
    
    //conversao para bytes (F register)
    public int getByte() {
        int value = 0;
        
        if (S) value |= 0b10000000;
        if (Z) value |= 0b01000000;
        if (H) value |= 0b00010000;
        if (PV) value |= 0b00000100;
        if (N) value |= 0b00000010;
        if (C) value |= 0b00000001;
        
        return value;
    }
    
    //conversao de bytes para flags
    public void setByte(int value) {
        S = (value & 0b10000000) != 0;
        Z = (value & 0b01000000) != 0;
        H = (value & 0b00010000) != 0;
        PV = (value & 0b00000100) != 0;
        N = (value & 0b00000010) != 0;
        C = (value & 0b00000001) != 0;
    }

}
