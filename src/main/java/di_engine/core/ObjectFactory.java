package di_engine.core;

import di_engine.utils.PackageScannerUtils;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ObjectFactory {
    private final WinterContext context; // TODO разобраться с контекстом
    private final Loader loader;
    private final List<Configurator> configurators = new LinkedList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new LinkedList<>();

    public ObjectFactory(WinterContext context, Loader loader) {
        this.context = context;
        this.loader = loader;
        this.findConfigurators();
    }

    @SneakyThrows
    private void findConfigurators() {
        Map<Class<?>, Collection<Class<?>>> ifc2AllLoadedImplMap = this.loader.getIfc2AllLoadedImplMap();
        Iterator<Class<?>> ifc2AllLoadedImplMapIterator = ifc2AllLoadedImplMap.keySet().iterator();

        while(true) {
            Class<?> ifc;
            Collection<Class<?>> configurators;
            Iterator<Class<?>> configuratorsIterator;
            Class<?> proxyConfiguratorImpl;
            do {
                if (!ifc2AllLoadedImplMapIterator.hasNext()) {
                    return;
                }

                ifc = ifc2AllLoadedImplMapIterator.next();
                if (PackageScannerUtils.getShortNameOfClass(ifc.getName()).equals("Configurator")) {
                    configurators = ifc2AllLoadedImplMap.get(ifc);
                    configuratorsIterator = configurators.iterator();

                    while(configuratorsIterator.hasNext()) {
                        proxyConfiguratorImpl = configuratorsIterator.next();
                        this.configurators.add((Configurator)proxyConfiguratorImpl.getDeclaredConstructor().newInstance());
                    }
                }
            } while(!PackageScannerUtils.getShortNameOfClass(ifc.getName()).equals("ProxyConfigurator"));

            configurators = ifc2AllLoadedImplMap.get(ifc);
            configuratorsIterator = configurators.iterator();

            while(configuratorsIterator.hasNext()) {
                proxyConfiguratorImpl = configuratorsIterator.next();
                this.proxyConfigurators.add((ProxyConfigurator)proxyConfiguratorImpl.getDeclaredConstructor().newInstance());
            }
        }
    }

    public <T> T createObject(Class<T> type) {
        T object = this.createNewInstance(type);
        this.configure(object);
        this.startInitMethods(object);
        return this.proxyConfigure(object, type);
    }

    @SneakyThrows
    private <T> T createNewInstance(Class<T> type) {
        return type.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (Configurator configurator : this.configurators) {
            configurator.configure(object);
        }
    }

    private <T> void startInitMethods(T object) {
    }

    private <T> T proxyConfigure(T object, Class<T> type) {
        T proxyRes = object;

        ProxyConfigurator proxyConfigurator;
        for(Iterator<ProxyConfigurator> proxyConfiguratorIterator = this.proxyConfigurators.iterator();
            proxyConfiguratorIterator.hasNext(); proxyRes = proxyConfigurator.configure(proxyRes, type)) {
            proxyConfigurator = proxyConfiguratorIterator.next();
        }
        return (T) proxyRes;
    }
}
