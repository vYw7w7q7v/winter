package di_engine.custom;

import di_engine.core.Application;
import di_engine.core.Configurator;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeConfigurator implements Configurator {
    public InjectByTypeConfigurator() {
    }

    @SneakyThrows
    public <T> void configure(T object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);

                Object injectingValue = Application.getAppContext().createObject(field.getType());

                field.set(object, injectingValue);
            }
        }

    }
}
