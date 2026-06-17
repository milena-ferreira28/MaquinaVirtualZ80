package assembler;

public class AssemblerMain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java assembler.AssemblerMain <arquivo.asm> [arquivo.obj]");
            System.out.println("Exemplo: java assembler.AssemblerMain programa.asm");
            return;
        }
        
        String inputFile = args[0];
        String outputFile = args.length > 1 ? args[1] : 
                           inputFile.replace(".asm", ".obj");
        
        Assembler assembler = new Assembler();
        boolean success = assembler.assemble(inputFile, outputFile);
        
        if (success) {
            System.out.println("Montagem concluída com sucesso!");
            System.out.println("Arquivo gerado: " + outputFile);
        } else {
            System.out.println("Erro na montagem:");
            System.out.println(assembler.getErrors());
            System.exit(1);
        }
    }
}