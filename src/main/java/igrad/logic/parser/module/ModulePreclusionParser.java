package igrad.logic.parser.module;

import java.util.Arrays;

public class ModulePreclusionParser {

    private String preclusionString;
    private String[] preclusionModules;

    public ModulePreclusionParser(String prerequisiteString) {
        this.preclusionString = prerequisiteString;
        this.preclusionModules = splitPreclusionModules(preclusionString);
    }

    public String[] splitPreclusionModules(String prerequisiteString) {
        String[] preclusionModules = prerequisiteString.split(" ");
        preclusionModules = Arrays.stream(preclusionModules).filter(x -> isModule(x)).toArray(String[]::new);
        preclusionModules = Arrays.stream(preclusionModules).
                map(x -> removeModulePunctuation(x)).toArray(String[]::new);

        return preclusionModules;
    }

    private boolean isModule(String module) {
        int numberCount = 0;
        char[] chars = module.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(Character.isDigit(chars[i])) {
                numberCount++;
            }
        }

        if (numberCount == 4) {
            return true;
        } else {
            return false;
        }
    }

    private String removeModulePunctuation(String module) {
        if (module.contains(",") || module.contains(".")) {
            return module.substring(0, module.length() - 2);
        } else {
            return module;
        }
    }

    public String[] getPreclusionModules() {
        return preclusionModules;
    }
}
