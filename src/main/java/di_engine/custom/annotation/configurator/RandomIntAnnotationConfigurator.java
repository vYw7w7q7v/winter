package di_engine.custom.annotation.configurator;

import di_engine.core.Configurator;
import di_engine.custom.annotation.RandomInt;
import lombok.SneakyThrows;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
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
//        if (roundEnv.processingOver())
//            return false;
//
//        Elements elements = processingEnv.getElementUtils();
//        Types types = processingEnv.getTypeUtils();
//        TypeMirror expected = elements.getTypeElement(Integer.class.getCanonicalName()).asType();
//
//        TypeMirror type;
//        for (Element element : roundEnv.getElementsAnnotatedWith(RandomInt.class)) {
//            type = element.asType();
//            if (!types.isSameType(type, expected))
//                processingEnv.getMessager()
//                        .printMessage(
//                                Diagnostic.Kind.ERROR,
//                                String.format("Invalid type of element %s!" +
//                                        "RandomInt annotation is not accepted for %s field!", element, type)
//                        );
//        }
        return false;
    }
}
