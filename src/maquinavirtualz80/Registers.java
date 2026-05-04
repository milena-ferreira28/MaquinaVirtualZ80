/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class Registers {
    
    public Flags flags;
    
    //Registradores de 8 bits
    public int A, B, C, D, E, H, L;
    
    //Registradores especiais de 16 bits
    public int PC; // Program Counter
    public int SP; // Stack Pointer
    public int IX;
    public int IY;
    
    //Contrutor
    public Registers(){
        flags = new Flags();
        reset();
    }
    
    //Resetar todos os registradores
    public void reset() {
        A = B = C = D = E = H = L = 0;
        PC = 0;
        SP = 0;
        IX = 0;
        IY = 0;
        flags.reset();
    }
    
    // REGISTRADORES DE 16 BITS (PARES)
    public int getBC(){
        return(B << 8) | C;
    }
    
    public void setBC(int value) {
        B = (value >> 8) & 0xFF;
        C = value & 0xFF;
    }
    
    public int getDE() {
        return (D << 8) | E;
    }
    
    public void setDE(int value) {
        D = (value >> 8) & 0xFF;
        E = value & 0xFF;
    }
    
    public int getHL() {
        return (H << 8) | L;
    }

    public void setHL(int value) {
        H = (value >> 8) & 0xFF;
        L = value & 0xFF;
    }
    
    //AF
    public int getAF() {
        return (A << 8) | flags.getByte();
    }
    
    public void setAF(int value) {
        A = (value >> 8) & 0xFF;
        flags.setByte(value & 0xFF);
    }
}
