package di_engine.core;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class Config {
    @Getter
    private final List<String> paths;
    private final Map<String, String> configValues;
    private final Map<Class, Class> implementations;

    public void putConfigValue(String key, String value) {
        this.configValues.put(key, value);
    }

    public String getConfigValue(String key) {
        return configValues.get(key);
    }

    public Config(List<String> paths, Map<String, String> configValues, Map<Class, Class> implementations) {
        this.paths = paths;
        this.configValues = configValues;
        this.implementations = implementations;
    }

    public Class<?> getDeclaredImplOfIfc(Class<?> ifc) {
        return implementations.get(ifc);
    }

}
