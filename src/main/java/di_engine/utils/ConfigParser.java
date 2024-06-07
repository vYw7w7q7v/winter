package di_engine.utils;

import di_engine.core.Config;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigParser {
    private static final String PATH_KEYWORD = "path";

    private ConfigParser() {
    }

    @SneakyThrows
    public static Config parseConfigFile(File configFile) {
        List<String> paths = new LinkedList<>();
        Map<String, String> configValues = new HashMap<>();
        Map<Class, Class> implementations = new HashMap<>();

        try {
            Scanner fileScanner = new Scanner(configFile);
            List<String> lines = new LinkedList<>();

            while(fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }

            List<String> valueLines = new LinkedList<>();
            List<String> implLines = new LinkedList<>();
            String valueLineRegex = "=+";
            String implLineRegex = ":+";
            Iterator<String> linesIterator = lines.iterator();

            String line;
            String[] valueLineSplit;
            String[] split;
            while(linesIterator.hasNext()) {
                line = linesIterator.next();
                valueLineSplit = line.split(valueLineRegex);
                split = line.split(implLineRegex);
                if (valueLineSplit.length != split.length) {
                    if (valueLineSplit.length == 2) {
                        valueLines.add(line);
                    }

                    if (split.length == 2) {
                        implLines.add(line);
                    }
                }
            }
            linesIterator = valueLines.iterator();

            String ifcName;
            while(linesIterator.hasNext()) {
                line = linesIterator.next();
                valueLineSplit = line.split(valueLineRegex);
                String valueID = valueLineSplit[0];
                ifcName = valueLineSplit[1];
                configValues.put(valueID, ifcName);
                if (valueID.equals(PATH_KEYWORD)) {
                    paths.add(ifcName);
                }
            }

            Map<String, String> fullClassNames = PackageScannerUtils.getFullClassNamesInDirectories(paths);

            for (String implLine : implLines) {
                line = implLine;
                split = line.split(implLineRegex);
                ifcName = fullClassNames.get(split[0]);
                if (ifcName == null) {
                    throw new RuntimeException("не найден интерфейс " + split[0]);
                }

                Class<?> ifc = Class.forName(ifcName);
                if (!ifc.isInterface()) {
                    throw new RuntimeException("класс " + ifc.getName() + " не является интерфейсом");
                }

                String implName = fullClassNames.get(split[1]);
                if (implName == null) {
                    throw new RuntimeException("не найден класс " + split[1]);
                }

                Class<?> impl = Class.forName(implName);
                if (!ReflectionUtils.checkIfClassImplementsIfc(impl, ifc)) {
                    throw new RuntimeException("класс " + impl + " должен реализовывать интерфейс " + ifc);
                }

                implementations.put(ifc, impl);
            }

            return new Config(paths, configValues, implementations);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException("конфигурационный файл не найден!!");
        }
    }
}