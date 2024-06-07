package di_engine.core;

public interface ProxyConfigurator {
    <T> T configure(T object, Class<T> tClass);
}
