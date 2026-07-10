package linker;

import java.io.*;
import java.util.*;

public class Linker {

    private ObjectReader reader;


    public Linker() {
        reader = new ObjectReader();
    }



    public boolean link(List<String> files, String output) {


        try {


            List<ObjectModule> modules =
                    new ArrayList<>();



            // =================================
            // Leitura dos módulos
            // =================================

            System.out.println("\n=== LEITURA DOS MÓDULOS ===");


            for(String file : files) {


                ObjectModule module =
                        reader.read(file);


                modules.add(module);


                System.out.println(
                        "Carregado: " + file
                );

            }




            // =================================
            // PRIMEIRA PASSAGEM
            // =================================

            System.out.println(
                    "\n=== PRIMEIRA PASSAGEM ==="
            );


            Map<String,Integer> globalSymbols =
                    new HashMap<>();


            int currentAddress = 0;



            for(int i = 0; i < modules.size(); i++) {


                ObjectModule module =
                        modules.get(i);



                module.baseAddress =
                        currentAddress;



                System.out.println(
                    "Módulo "
                    + (i+1)
                    + " | Base: "
                    + currentAddress
                    + " | Tamanho: "
                    + module.code.size()
                    + " bytes"
                );



                // adiciona símbolos públicos

                for(Map.Entry<String,Integer> entry :
                        module.publicSymbols.entrySet()) {



                    String symbol =
                            entry.getKey();



                    int address =
                            entry.getValue()
                            + module.baseAddress;



                    if(globalSymbols.containsKey(symbol)) {

                        System.out.println(
                            "ERRO: símbolo duplicado "
                            + symbol
                        );

                        return false;

                    }



                    globalSymbols.put(
                            symbol,
                            address
                    );


                }



                currentAddress +=
                        module.code.size();

            }




            // Mostra tabela global

            System.out.println(
                    "\n=== TABELA GLOBAL DE SÍMBOLOS ==="
            );


            System.out.println(
                    String.format(
                            "%-15s %s",
                            "Símbolo",
                            "Endereço"
                    )
            );


            System.out.println(
                    "-----------------------------"
            );


            for(Map.Entry<String,Integer> entry :
                    globalSymbols.entrySet()) {


                System.out.println(
                    String.format(
                        "%-15s %04X",
                        entry.getKey(),
                        entry.getValue()
                    )
                );

            }




            // =================================
            // SEGUNDA PASSAGEM
            // =================================

            System.out.println(
                    "\n=== SEGUNDA PASSAGEM ==="
            );



            for(ObjectModule module : modules) {


                relocate(module);


                resolveExternalSymbols(
                        module,
                        globalSymbols
                );

            }





            // =================================
            // Monta código final
            // =================================

            List<Integer> finalCode =
                    new ArrayList<>();



            for(ObjectModule module : modules) {


                finalCode.addAll(
                        module.code
                );

            }





            // =================================
            // Escreve arquivo final
            // =================================

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(output)
                    );



            writer.write("CODE\n");


            for(Integer value : finalCode) {


                writer.write(
                    String.format(
                        "%02X\n",
                        value
                    )
                );

            }



            writer.close();



            System.out.println(
                    "\nArquivo gerado: "
                    + output
            );


            return true;



        }
        catch(Exception e) {


            e.printStackTrace();

            return false;

        }

    }






    // =================================
    // Relocação
    // =================================

    private void relocate(ObjectModule module) {



        for(Integer pos :
                module.relocation) {



            int low =
                    module.code.get(pos);



            int high =
                    module.code.get(pos+1);



            int address =
                    low | (high << 8);



            address +=
                    module.baseAddress;



            module.code.set(
                    pos,
                    address & 0xFF
            );


            module.code.set(
                    pos+1,
                    (address >> 8) & 0xFF
            );



            System.out.println(
                "Relocado endereço na posição "
                + pos
                + " -> "
                + address
            );

        }

    }






    // =================================
    // Símbolos externos
    // =================================

    private void resolveExternalSymbols(
            ObjectModule module,
            Map<String,Integer> symbols) {



        for(String symbol :
                module.externalSymbols) {



            if(!symbols.containsKey(symbol)) {


                System.out.println(
                    "ERRO: símbolo externo não encontrado: "
                    + symbol
                );


                continue;

            }



            int address =
                    symbols.get(symbol);




            for(Integer pos :
                    module.relocation) {



                module.code.set(
                        pos,
                        address & 0xFF
                );


                module.code.set(
                        pos+1,
                        (address >> 8) & 0xFF
                );

            }



            System.out.println(
                "Símbolo resolvido: "
                + symbol
                + " -> "
                + address
            );

        }

    }

}