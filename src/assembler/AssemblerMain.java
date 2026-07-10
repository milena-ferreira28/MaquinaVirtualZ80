package assembler;

import java.io.File;

public class AssemblerMain {

    public static void main(String[] args) {

        File pasta = new File("programs");

        if (!pasta.exists() || !pasta.isDirectory()) {
            System.out.println("Pasta programs não encontrada.");
            return;
        }

        File[] arquivosAsm = pasta.listFiles((dir, nome) -> 
        nome.endsWith(".asm") && !nome.endsWith(".exp.asm")
);

        if (arquivosAsm == null || arquivosAsm.length == 0) {
            System.out.println("Nenhum arquivo .asm encontrado na pasta programs.");
            return;
        }

        System.out.println("Arquivos encontrados: " + arquivosAsm.length);

        for (File arquivo : arquivosAsm) {

            String inputFile = arquivo.getPath();

            String expandedFile = inputFile.substring(0, inputFile.length() - 4) + ".exp.asm";
            String outputFile = inputFile.substring(0, inputFile.length() - 4) + ".obj";

            System.out.println("\n================================");
            System.out.println("Processando: " + inputFile);
            System.out.println("================================");


            // Processador de macros
            System.out.println("=== PROCESSADOR DE MACROS ===");

            MacroProcessor macroProcessor = new MacroProcessor();

            boolean macroOk = macroProcessor.process(inputFile, expandedFile);

            if (!macroOk) {
                System.out.println("Erro no processador de macros:");
                System.out.println(macroProcessor.getErrors());
                continue;
            }

            System.out.println("Expandido: " + expandedFile);


            // Montador
            System.out.println("\n=== MONTADOR DE DOIS PASSOS ===");

            Assembler assembler = new Assembler();

            boolean assembleOk = assembler.assemble(expandedFile, outputFile);

            if (!assembleOk) {
                System.out.println("Erro na montagem:");
                System.out.println(assembler.getErrors());
                continue;
            }

            System.out.println("Objeto gerado: " + outputFile);
            System.out.println("Processo concluído com sucesso.");
        }


        System.out.println("\n================================");
        System.out.println("Todos os arquivos foram processados.");
        System.out.println("================================");
    }
}