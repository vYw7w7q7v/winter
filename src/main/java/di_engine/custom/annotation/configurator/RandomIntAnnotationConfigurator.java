package di_engine.custom.annotation.configurator;

import di_engine.core.Configurator;
import di_engine.custom.annotation.RandomInt;
import lombok.SneakyThrows;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@SupportedAnnotationTypes("com.example.SomeAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RandomIntAnnotationConfigurator extends AbstractProcessor implements Configurator {

    @Override
    @SneakyThrows
    public <T> void configure(T object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RandomInt.class)) {
                RandomInt annotation = field.getAnnotation(RandomInt.class);

                int randomizedValue = ThreadLocalRandom.current().nextInt(
                        annotation.lowerBound(),
                        annotation.upperBound()
                );

                field.setAccessible(true);

                if (field.getType().equals(int.class) || field.getType().equals(Integer.class)
                                || field.getType().equals(double.class) || field.getType().equals(Double.class)
                ) {
                    field.set(object, randomizedValue);
                } else {
                    throw new RuntimeException("недопустимый тип поля аннотации RandomInt!!");
                }
            }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO проверить допустимые поля аннотации и условие upperBound > lowerBound на этапе процессинга
        return false;
    }
}
