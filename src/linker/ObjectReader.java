package linker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ObjectReader {

    public ObjectModule read(String filename) throws IOException {

        ObjectModule module = new ObjectModule();

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        String section = "";

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty())
                continue;

            if (line.equals("SYMBOLS")) {
                section = "SYMBOLS";
                continue;
            }

            if (line.equals("PUBLIC")) {
                section = "PUBLIC";
                continue;
            }

            if (line.equals("EXTERN")) {
                section = "EXTERN";
                continue;
            }

            if (line.equals("RELOC")) {
                section = "RELOC";
                continue;
            }

            if (line.equals("CODE")) {
                section = "CODE";
                continue;
            }

            switch (section) {

                case "SYMBOLS": {
                    String[] parts = line.split("\\s+");

                    if (parts.length >= 2) {
                        module.publicSymbols.put(parts[0],
                                Integer.parseInt(parts[1]));
                    }

                    break;
                }

                case "PUBLIC": {
                    String[] parts = line.split("\\s+");

                    if (parts.length >= 2) {
                        module.publicSymbols.put(parts[0],
                                Integer.parseInt(parts[1]));
                    }

                    break;
                }

                case "EXTERN":
                    module.externalSymbols.add(line);
                    break;

                case "RELOC":
                    module.relocation.add(Integer.parseInt(line));
                    break;

                case "CODE":
                    module.code.add(Integer.parseInt(line, 16));
                    break;
            }

        }

        reader.close();

        return module;
    }

}