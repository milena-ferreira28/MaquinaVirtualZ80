package assembler;

import java.util.ArrayList;
import java.util.List;

public class MacroDefinition {

    public String name;
    public List<String> parameters;
    public List<String> body;
    public int endLine;

    public MacroDefinition(String name, List<String> parameters) {
        this.name = name;
        this.parameters = new ArrayList<>(parameters);
        this.body = new ArrayList<>();
        this.endLine = -1;
    }

    @Override
    public String toString() {
        return String.format("%-15s params: %-20s corpo: %d linhas",
                name, parameters, body.size());
    }
}