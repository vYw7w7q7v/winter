package di_engine.core;

import di_engine.utils.PackageScannerUtils;
import di_engine.utils.ReflectionUtils;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Loader {
    private Config config;
    @Getter
    private Collection<Class<?>> loadedClasses = new ConcurrentLinkedQueue<>();
    @Getter
    private Map<Class<?>, Collection<Class<?>>> ifc2AllLoadedImplMap;

    public Loader(Config config) {
        this.config = config;
        this.loadClasses();
        this.ifc2AllLoadedImplMap = ReflectionUtils.getIfc2AllImplMap(this.loadedClasses);
    }

    @SneakyThrows
    private void loadClasses() {
        List<String> paths = new LinkedList<>(this.config.getPaths());

        paths.add("src/main/java/di_engine"); // TODO исправить пути по дефолту

        Collection<String> names = PackageScannerUtils.getFullClassNamesInDirectories(paths).values();

        for (String name : names) {
            loadedClasses.add(Class.forName(name));
        }

    }

}
