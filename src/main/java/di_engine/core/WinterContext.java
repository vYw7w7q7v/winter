package di_engine.core;

import lombok.Getter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WinterContext {
    @Getter
    private final Config config;
    private final Loader loader;
    private ObjectFactory objectFactory;
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();
    private final Map<String, Connection> connectionPull = new HashMap<>();

    public void putConnection(String key, Connection connection) {
        this.connectionPull.put(key, connection);
    }

    public Connection getConnection(String key) {
        return connectionPull.get(key);
    }

    public WinterContext(Config config, Loader loader) {
        this.config = config;
        this.loader = loader;
    }


    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    private Class<?> tryToGetImplOfIfc(Class<?> ifc) {
        Collection<Class<?>> loadedImplementations = loader.getIfc2AllLoadedImplMap().get(ifc);
        if (loadedImplementations != null && !loadedImplementations.isEmpty()) {
            if (loadedImplementations.size() == 1) {
                return loadedImplementations.iterator().next();
            } else {
                Class<?> implClass = config.getDeclaredImplOfIfc(ifc);
                if (implClass == null) {
                    throw new RuntimeException("Не указано, какой из загруженных классов реализует интерфейс " + ifc.getName());
                } else {
                    return implClass;
                }
            }
        } else {
            List<String> var10002 = config.getPaths();
            throw new RuntimeException("В сканируемых пакетах " + var10002 +
                    " не загружен класс, реализующий интерфейс " + ifc.getName());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> clazz) {
        if (this.objectFactory == null) {
            throw new RuntimeException("В контекст не добавлена фабрика!");
        } else {
            Class<?> implClass = clazz.isInterface() ? tryToGetImplOfIfc(clazz) : clazz;
            if (implClass.isAnnotationPresent(Singleton.class)) {
                Object cachedSingltoneObject = cache.get(implClass);
                if (cachedSingltoneObject != null) {
                    return (T) cachedSingltoneObject;
                } else {
                    Object createdNonCachedSingltoneObject = objectFactory.createObject(implClass);
                    this.cache.put(implClass, createdNonCachedSingltoneObject);
                    return (T) createdNonCachedSingltoneObject;
                }
            } else {
                Object createdNonSingletonObject = objectFactory.createObject(implClass);
                return (T) createdNonSingletonObject;
            }
        }
    }

    public void createAllNotLazySingletons2Cache() {

        for (Class<?> aClass : loader.getLoadedClasses()) {
            if (aClass.isAnnotationPresent(Singleton.class)
                    && !aClass.isInterface()
                    && !(aClass.getAnnotation(Singleton.class)).lazy()) {
                createObject(aClass);
            }
        }

    }

}
