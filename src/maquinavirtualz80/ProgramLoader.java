/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maquinavirtualz80;

/**
 *
 * @author milly
 */

import java.io.BufferedReader;
import java.io.FileReader;


public class ProgramLoader {

    public static void load(String filename, Memory memory) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;
            int address = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) continue;

                int value = Integer.parseInt(line, 16);
                memory.write(address++, value);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Erro ao carregar programa: " + e.getMessage());
        }
    }
}