package linker;

import java.util.*;

public class ObjectModule {

    public Map<String,Integer> publicSymbols = new HashMap<>();

    public List<String> externalSymbols = new ArrayList<>();

    public List<Integer> relocation = new ArrayList<>();

    public List<Integer> code = new ArrayList<>();

    public int baseAddress = 0;

}