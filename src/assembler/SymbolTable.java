package assembler;

import java.util.*;

public class SymbolTable {

    private Map<String, Integer> symbols;

    private Set<String> publicSymbols;

    private Set<String> externalSymbols;

    public SymbolTable() {
        symbols = new HashMap<>();
        publicSymbols = new LinkedHashSet<>();
        externalSymbols = new LinkedHashSet<>();
    }

    public boolean addSymbol(String name, int address) {

        if (symbols.containsKey(name))
            return false;

        symbols.put(name, address);
        return true;
    }

    public int getAddress(String name) {
        return symbols.getOrDefault(name, -1);
    }

    // -----------------------------
    // PUBLIC
    // -----------------------------

    public void addPublic(String name) {
        publicSymbols.add(name);
    }

    public Set<String> getPublicSymbols() {
        return publicSymbols;
    }

    // -----------------------------
    // EXTERN
    // -----------------------------

    public void addExternal(String name) {
        externalSymbols.add(name);
    }

    public Set<String> getExternalSymbols() {
        return externalSymbols;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void print() {

        System.out.println("\n=== TABELA DE SÍMBOLOS ===");

        for (Map.Entry<String, Integer> e : symbols.entrySet()) {
            System.out.printf("%s = %04X\n", e.getKey(), e.getValue());
        }

        if (!publicSymbols.isEmpty()) {

            System.out.println("\nPUBLIC:");

            for (String s : publicSymbols)
                System.out.println(s);
        }

        if (!externalSymbols.isEmpty()) {

            System.out.println("\nEXTERN:");

            for (String s : externalSymbols)
                System.out.println(s);
        }
    }
}