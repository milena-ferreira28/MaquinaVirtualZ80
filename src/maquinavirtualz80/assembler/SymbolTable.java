package maquinavirtualz80.assembler;

import java.util.*;

public class SymbolTable {
    private Map<String, Integer> symbols;
    
    public SymbolTable() {
        symbols = new HashMap<>();
    }
    
    public boolean addSymbol(String name, int address) {
        if (symbols.containsKey(name)) return false;
        symbols.put(name, address);
        return true;
    }
    
    public int getAddress(String name) {
        return symbols.getOrDefault(name, -1);
    }
    
    public void print() {
        System.out.println("\n=== TABELA DE SÍMBOLOS ===");
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            System.out.printf("%s = $%04X\n", entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== TABELA DE SÍMBOLOS ===\n");
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            sb.append(String.format("%s = $%04X\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}