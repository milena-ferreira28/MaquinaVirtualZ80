package assembler;

public class AssemblerMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java assembler.AssemblerMain <arquivo.asm> [arquivo.obj]");
            System.out.println("Exemplo: java assembler.AssemblerMain programs/exemplo.asm");
            return;
        }

        String inputFile = args[0];
        String expandedFile = inputFile.endsWith(".asm")
                ? inputFile.substring(0, inputFile.length() - 4) + ".exp.asm"
                : inputFile + ".exp.asm";
        String outputFile = args.length > 1
                ? args[1]
                : inputFile.replace(".asm", ".obj");

        // Pre-processamento: expansao de macros
        System.out.println("=== PROCESSADOR DE MACROS ===");
        System.out.println("Entrada : " + inputFile);

        MacroProcessor macroProcessor = new MacroProcessor();
        boolean macroOk = macroProcessor.process(inputFile, expandedFile);

        if (!macroOk) {
            System.out.println("Erro no processador de macros:");
            System.out.println(macroProcessor.getErrors());
            System.exit(1);
        }

        System.out.println("Expandido: " + expandedFile);

        // Montagem do arquivo expandido
        System.out.println("\n=== MONTADOR DE DOIS PASSOS ===");
        System.out.println("Entrada : " + expandedFile);

        Assembler assembler = new Assembler();
        boolean assembleOk = assembler.assemble(expandedFile, outputFile);

        if (!assembleOk) {
            System.out.println("Erro na montagem:");
            System.out.println(assembler.getErrors());
            System.exit(1);
        }

        System.out.println("Objeto  : " + outputFile);
        System.out.println("\nProcesso concluido com sucesso.");
    }
}