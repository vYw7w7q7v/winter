package di_engine.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PackageScannerUtils {
    private PackageScannerUtils() {
    }

    public static List<String> scanClassesInDirectory(String directory) {
        try {
            File dir = new File(directory);
            List<String> foundedClasses = new LinkedList();
            File[] innerFiles = dir.listFiles();
            if (innerFiles == null) {
                throw new RuntimeException("некорректно указан путь: " + directory);
            } else {
                File[] var4 = innerFiles;
                int var5 = innerFiles.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    File innerFile = var4[var6];
                    if (innerFile.isDirectory()) {
                        foundedClasses.addAll(scanClassesInDirectory(directory + "/" + innerFile.getName()));
                    }

                    if (innerFile.getName().endsWith(".java")) {
                        String var10000 = directory.replaceAll("(/)+", ".");
                        String foundedClassName = var10000 + "." + innerFile.getName();
                        String formattedClassName = foundedClassName.substring(0, foundedClassName.length() - 5).replaceFirst("src\\.main\\.java\\.", "");
                        foundedClasses.add(formattedClassName);
                    }
                }

                return foundedClasses;
            }
        } catch (Throwable var10) {
            throw var10;
        }
    }

    public static Map<String, String> getFullClassNamesInDirectory(String directory) {
        try {
            File dir = new File(directory);
            Map<String, String> classNameMap = new HashMap();
            File[] innerFiles = dir.listFiles();
            if (innerFiles == null) {
                throw new RuntimeException("некорректно указан путь: " + directory);
            } else {
                File[] var4 = innerFiles;
                int var5 = innerFiles.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    File innerFile = var4[var6];
                    if (innerFile.isDirectory()) {
                        classNameMap.putAll(getFullClassNamesInDirectory(directory + "/" + innerFile.getName()));
                    }

                    if (innerFile.getName().endsWith(".java")) {
                        String var10000 = directory.replaceAll("(/)+", ".");
                        String foundedClassName = var10000 + "." + innerFile.getName();
                        String formattedClassName = foundedClassName.substring(0, foundedClassName.length() - 5).replaceFirst("src\\.main\\.java\\.", "");
                        classNameMap.put(innerFile.getName().substring(0, innerFile.getName().length() - 5), formattedClassName);
                    }
                }

                return classNameMap;
            }
        } catch (Throwable var10) {
            throw var10;
        }
    }

    public static Map<String, String> getFullClassNamesInDirectories(List<String> directories) {
        Map<String, String> res = new HashMap();
        Iterator var2 = directories.iterator();

        while(var2.hasNext()) {
            String directory = (String)var2.next();
            res.putAll(getFullClassNamesInDirectory(directory));
        }

        return res;
    }

    public static String getShortNameOfClass(String fullName) {
        String[] splitRes = fullName.split("\\.");
        return splitRes[splitRes.length - 1];
    }
}
