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
        
        CPU cpu = new CPU();
        new GUI(cpu);
        /*
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
        
        // ============================
        // TESTE 9 - ADD
        // ============================
        CPU cpu5 = new CPU();
        
        cpu5.getMemory().write(0, 0x3E); //LD A , n
        cpu5.getMemory().write(1, 0x05); //ADD = 5
        
        cpu5.getMemory().write(2, 0xC6); //ADD A , n
        cpu5.getMemory().write(3, 0x03); // +3
        
        cpu5.getMemory().write(4, 0x76); //HALT
        
        cpu5.run();
        
        System.out.println("\nTESTE 9 - ADD:");
        System.out.println("A = " + cpu5.getRegisters().A);
        System.out.println("Zero = " + cpu5.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu5.getRegisters().flags.getSign());
        System.out.println("Carry = " + cpu5.getRegisters().flags.getCarry());
        
        // ============================
        // TESTE 10 - SUB
        // ============================
        CPU cpu6 = new CPU();

        cpu6.getMemory().write(0, 0x3E); // LD A, n
        cpu6.getMemory().write(1, 10);   // A = 10

        cpu6.getMemory().write(2, 0xD6); // SUB A, n
        cpu6.getMemory().write(3, 3);    // -3

        cpu6.getMemory().write(4, 0x76); // HALT

        cpu6.run();

        System.out.println("\nTESTE 10 — SUB:");
        System.out.println("A = " + cpu6.getRegisters().A);
        System.out.println("Zero = " + cpu6.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu6.getRegisters().flags.getSign());
        System.out.println("Carry = " + cpu6.getRegisters().flags.getCarry());
        
        // ============================
        // TESTE 11 - AND
        // ============================
        CPU cpu7 = new CPU();

        cpu7.getMemory().write(0, 0x3E);
        cpu7.getMemory().write(1, 0b10101010); // 170

        cpu7.getMemory().write(2, 0xE6);
        cpu7.getMemory().write(3, 0b11110000); // 240

        cpu7.getMemory().write(4, 0x76);

        cpu7.run();

        System.out.println("\nTESTE 11 — AND:");
        System.out.println("A = " + cpu7.getRegisters().A);
        System.out.println("Zero = " + cpu7.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu7.getRegisters().flags.getSign());
        System.out.println("Carry = " + cpu7.getRegisters().flags.getCarry());
        
        // ============================
        // TESTE 12 - OR
        // ============================
        CPU cpu8 = new CPU();

        cpu8.getMemory().write(0, 0x3E); //LD A, n
        cpu8.getMemory().write(1, 0x0A); // 1010

        cpu8.getMemory().write(2, 0xF6); //OR n
        cpu8.getMemory().write(3, 0X05); // 0101 ou 5

        cpu8.getMemory().write(4, 0x76); //halt

        cpu8.run();

        System.out.println("\nTESTE 12 — OR:");
        System.out.println("A = " + cpu8.getRegisters().A);
        System.out.println("Zero = " + cpu8.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu8.getRegisters().flags.getSign());
        
        // ============================
        // TESTE 13 - XOR
        // ============================
        CPU cpu9 = new CPU();

        cpu9.getMemory().write(0, 0x3E); //LD A, n
        cpu9.getMemory().write(1, 0x0A); // 1010

        cpu9.getMemory().write(2, 0xEE); //XOR n
        cpu9.getMemory().write(3, 0x0C); // 12 (1100)

        cpu9.getMemory().write(4, 0x76); //halt

        cpu9.run();

        System.out.println("\nTESTE 13 — XOR:");
        System.out.println("A = " + cpu9.getRegisters().A);
        System.out.println("Zero = " + cpu9.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu9.getRegisters().flags.getSign());  
        
        // ============================
        // TESTE 14 - CP (igual)
        // ============================
        CPU cpu10 = new CPU();

        cpu10.getMemory().write(0, 0x3E); //LD A, n
        cpu10.getMemory().write(1, 0x0A); // A = 1010 (10)

        cpu10.getMemory().write(2, 0xFE); //CP n
        cpu10.getMemory().write(3, 0x0A); // compara com A (que é 10)

        cpu10.getMemory().write(4, 0x76); //halt

        cpu10.run();

        System.out.println("\nTESTE 14 — CP:");
        System.out.println("A = " + cpu10.getRegisters().A);
        System.out.println("Zero = " + cpu10.getRegisters().flags.getZero());
        System.out.println("Sign = " + cpu10.getRegisters().flags.getSign()); 
        System.out.println("Carry = " + cpu10.getRegisters().flags.getCarry());        
        
        // ============================
        // TESTE 15 - JP
        // ============================
        CPU cpu11 = new CPU();
        
        //PULA DIRETO PRA POSIÇÃO 5
        cpu11.getMemory().write(0, 0xC3); //JP
        cpu11.getMemory().write(1, 0x05); // low
        cpu11.getMemory().write(2, 0x00); // high
        
        // Isso aqui deve ser IGNORADO
        cpu11.getMemory().write(3, 0x3E); // LD A
        cpu11.getMemory().write(4, 0xFF); // 255

        // Destino do salto
        cpu11.getMemory().write(5, 0x3E); // LD A
        cpu11.getMemory().write(6, 0x0A); // A = 10

        cpu11.getMemory().write(7, 0x76); // HALT

        cpu11.run();

        System.out.println("\nTESTE 15 — JP:");
        System.out.println("A = " + cpu11.getRegisters().A);
        
        // ============================
        // TESTE 16 - JR (forward)
        // ============================
        CPU cpu12 = new CPU();
        
        //JR +2
        cpu12.getMemory().write(0, 0x18); //JR
        cpu12.getMemory().write(1, 0x02); // +2
        
        // Isso aqui deve ser IGNORADO (pulado)
        cpu12.getMemory().write(2, 0x3E); // LD A
        cpu12.getMemory().write(3, 0xFF); // 255

        // Destino do salto
        cpu12.getMemory().write(4, 0x3E); // LD A
        cpu12.getMemory().write(5, 0x0A); // A = 10

        cpu12.getMemory().write(6, 0x76); // HALT

        cpu12.run();

        System.out.println("\nTESTE 16 — JR forward:");
        System.out.println("A = " + cpu12.getRegisters().A);
        // ============================
        // TESTE 17 - CALL + RET
        // ============================    
        CPU cpu13 = new CPU();
        
        //CALL 0006
        cpu13.getMemory().write(0, 0xCD);
        cpu13.getMemory().write(1, 0x06);
        cpu13.getMemory().write(2,0x00);
        
        //CÓDIGO PRINCIPAL (EXECUTA DEPOIS DO RET)
        cpu13.getMemory().write(3, 0x3E); // LD A
        cpu13.getMemory().write(4, 0x01); // A = 1
        cpu13.getMemory().write(5, 0x76); // HALT

        // "função" (endereço 6)
        cpu13.getMemory().write(6, 0x3E); // LD A
        cpu13.getMemory().write(7, 0x0A); // A = 10
        cpu13.getMemory().write(8, 0xC9); // RET

        cpu13.run();

        System.out.println("\nTESTE 17 — CALL + RET:");
        System.out.println("A = " + cpu13.getRegisters().A);
        
        // =========================
        // TESTE 18 — PUSH BC
        // =========================
        CPU cpu14 = new CPU();

        cpu14.getRegisters().B = 0x12;
        cpu14.getRegisters().C = 0x34;

        cpu14.getMemory().write(0, 0xC5); // PUSH BC
        cpu14.getMemory().write(1, 0x76); // HALT

        cpu14.run();

        System.out.println("\nTESTE 18 — PUSH BC:");
        System.out.println("SP = " + Integer.toHexString(cpu14.getRegisters().SP));
        
        // =========================
        // TESTE 19 — POP BC
        // =========================
        CPU cpu15 = new CPU();

        // simula um PUSH manual
        cpu15.getRegisters().SP = 0xFFFE;

        cpu15.getMemory().write(0xFFFD, 0x12); // B (high)
        cpu15.getMemory().write(0xFFFC, 0x34); // C (low)

        cpu15.getRegisters().SP = 0xFFFC;

        cpu15.getMemory().write(0, 0xC1); // POP BC
        cpu15.getMemory().write(1, 0x76); // HALT

        cpu15.run();

        System.out.println("\nTESTE 29 — POP BC:");
        System.out.println("B = " + Integer.toHexString(cpu15.getRegisters().B));
        System.out.println("C = " + Integer.toHexString(cpu15.getRegisters().C));
        
        // =========================
        // TESTE 20 — PUSH + POP
        // =========================
        CPU cpu16 = new CPU();

        cpu16.getRegisters().B = 0xAA;
        cpu16.getRegisters().C = 0xBB;

        cpu16.getMemory().write(0, 0xC5); // PUSH BC
        cpu16.getMemory().write(1, 0xC1); // POP BC
        cpu16.getMemory().write(2, 0x76); // HALT

        cpu16.run();

        System.out.println("\nTESTE 20 — PUSH + POP:");
        System.out.println("B = " + Integer.toHexString(cpu16.getRegisters().B));
        System.out.println("C = " + Integer.toHexString(cpu16.getRegisters().C));
    
        // ============================
        // TESTE 21 — Program Loader
        // ============================
        CPU cpu17 = new CPU();

        // CAMINHO DO ARQUIVO (ajusta pro teu PC)
        ProgramLoader.load("/maquinavirtualz80/programa.txt", cpu17.getMemory());

        cpu17.run();

        System.out.println("\nTESTE 21 — LOADER:");
        System.out.println("A = " + cpu17.getRegisters().A);
*/
    }
}