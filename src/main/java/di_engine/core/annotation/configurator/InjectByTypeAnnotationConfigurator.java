package di_engine.core.annotation.configurator;

import di_engine.core.WinterApplication;
import di_engine.core.Configurator;
import di_engine.core.annotation.InjectByType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationConfigurator implements Configurator {
    public InjectByTypeAnnotationConfigurator() {
    }

    @Override
    @SneakyThrows
    public <T> void configure(T object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);

                Object injectingValue = WinterApplication.getAppContext().createObject(field.getType());

                field.set(object, injectingValue);
            }
        }

    }
}
