/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */
public class Memory {
    
    // 64 KB = 65536 bytes
    private int [] memory;
    
    //construtor
    public Memory() {
        memory = new int[65536]; //CRIA A RAM DA MÁQUINA
        reset();
    }
    
    // lê um valor da memória
    public int read(int adress) { // LÊ DA MEMÓRIA
        if (adress >= 0 && adress < 65536) {
            return memory[adress];
        } else {
            System.out.println("Erro: endereço inválido para leitura");
            return -1;
        }
    }
    
    // escreve um valor na memória
    public void write(int adress, int value) { // ESCREVE NA MEMÓRIA
        if (adress >= 0 && adress < 65536) {
            memory[adress] = value & 0xFF; //garante 1 byte
        } else {
            System.out.println("Erro: endereço inválido para escrita");
        }
    }
    
    //limpa toda a memória
    public void reset() { //RESETA TODA A MAQUINA
        for (int i = 0; i < 65536; i++) {
            memory[i] = 0;
        }
    }
}
