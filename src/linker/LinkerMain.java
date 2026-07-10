package linker;

import java.util.ArrayList;
import java.util.List;


public class LinkerMain {


    public static void main(String[] args) {
        
        System.out.println("Quantidade de argumentos: " + args.length);

        for(String arg : args){
            System.out.println("ARG: " + arg);
        }

        if(args.length < 3) {


            System.out.println(
                "Uso:"
            );


            System.out.println(
                "java linker.LinkerMain "
                + "<arquivo1.obj> <arquivo2.obj> ... <saida.exe>"
            );


            return;

        }



        List<String> files =
                new ArrayList<>();



        for(int i = 0; i < args.length-1; i++) {


            files.add(args[i]);

        }



        String output =
                args[args.length-1];




        System.out.println(
                "================================"
        );

        System.out.println(
                "              LIGADOR"
        );

        System.out.println(
                "================================"
        );




        Linker linker =
                new Linker();



        boolean success =
                linker.link(
                    files,
                    output
                );




        if(success) {


            System.out.println(
                "\nLigação finalizada com sucesso."
            );


        }
        else {


            System.out.println(
                "\nErro durante a ligação."
            );

        }

    }

}