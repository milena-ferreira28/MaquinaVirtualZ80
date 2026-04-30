/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class Main {

    public static void main(String[] args) {

        // =========================
        // TESTE 1 — MEMÓRIA
        // =========================
        Memory mem = new Memory();
        mem.write(10, 55);
        System.out.println("Valor armazenado: " + mem.read(10));

        // =========================
        // TESTE 2 — REGISTRADORES
        // =========================
        Registers r = new Registers();

        r.B = 0x12;
        r.C = 0x34;
        System.out.println("BC = " + Integer.toHexString(r.getBC()));

        r.setHL(0xABCD);
        System.out.println("H = " + Integer.toHexString(r.H));
        System.out.println("L = " + Integer.toHexString(r.L));

        // =========================
        // TESTE 3 — FLAGS
        // =========================
        Flags f = new Flags();
        f.setZero(true);
        f.setCarry(true);

        int byteFlags = f.getByte();
        System.out.println("Flags em byte: " + Integer.toBinaryString(byteFlags));

        Flags f2 = new Flags();
        f2.setByte(byteFlags);
        System.out.println("Zero: " + f2.getZero());
        System.out.println("Carry: " + f2.getCarry());

        // =========================
        // TESTE 4 — AF
        // =========================
        r.A = 0x12;
        r.flags.setZero(true);
        r.flags.setCarry(true);

        int af = r.getAF();
        System.out.println("AF = " + Integer.toHexString(af));

        Registers r2 = new Registers();
        r2.setAF(af);

        System.out.println("A = " + Integer.toHexString(r2.A));
        System.out.println("Zero = " + r2.flags.getZero());
        System.out.println("Carry = " + r2.flags.getCarry());

        // =========================
        // TESTE 5 — CPU (LD + INC)
        // =========================
        CPU cpu = new CPU();

        cpu.getMemory().write(0, 0x3E); // LD A, n
        cpu.getMemory().write(1, 0x05); // A = 5
        cpu.getMemory().write(2, 0x3C); // INC A
        cpu.getMemory().write(3, 0x76); // HALT

        cpu.run();

        System.out.println("\nTESTE 5:");
        System.out.println("A = " + cpu.getRegisters().A);
        System.out.println("Zero = " + cpu.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu.getRegisters().flags.getSign());

        // =========================
        // TESTE 6 — OVERFLOW (255 → 0)
        // =========================
        CPU cpu2 = new CPU();

        cpu2.getMemory().write(0, 0x3E);
        cpu2.getMemory().write(1, 0xFF); // 255
        cpu2.getMemory().write(2, 0x3C); // INC
        cpu2.getMemory().write(3, 0x76);

        cpu2.run();

        System.out.println("\nTESTE 6:");
        System.out.println("A = " + cpu2.getRegisters().A);
        System.out.println("Zero = " + cpu2.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu2.getRegisters().flags.getSign());

        // =========================
        // TESTE 7 — SIGN (127 → 128)
        // =========================
        CPU cpu3 = new CPU();

        cpu3.getMemory().write(0, 0x3E);
        cpu3.getMemory().write(1, 0x7F); // 127
        cpu3.getMemory().write(2, 0x3C); // INC
        cpu3.getMemory().write(3, 0x76);

        cpu3.run();

        System.out.println("\nTESTE 7:");
        System.out.println("A = " + cpu3.getRegisters().A);
        System.out.println("Zero = " + cpu3.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu3.getRegisters().flags.getSign());
        
        // =========================
        // TESTE 8 — DEC A (1 → 0)
        // =========================
CPU cpu4 = new CPU();

        cpu4.getMemory().write(0, 0x3E); // LD A, n
        cpu4.getMemory().write(1, 0x01); // A = 1
        cpu4.getMemory().write(2, 0x3D); // DEC A
        cpu4.getMemory().write(3, 0x76); // HALT

        cpu4.run();

        System.out.println("\nTESTE 8:");
        System.out.println("A = " + cpu4.getRegisters().A);
        System.out.println("Zero = " + cpu4.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu4.getRegisters().flags.getSign());
        
        
    }
}