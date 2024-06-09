package di_engine.custom.annotation.configurator;

import di_engine.core.ProxyConfigurator;
import di_engine.custom.annotation.ReplaceMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

public class ReplaceMethodAnnotationProxyConfigurator implements ProxyConfigurator {

    @Override
    public <T> T configure(T object, Class<T> tClass) {

        Method[] methods = tClass.getMethods();
        List<Method> methodsForReplace = new LinkedList<>();

        for (Method method: methods) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(ReplaceMethod.class)) {
                methodsForReplace.add(method);
            }
        }
        if (methodsForReplace.isEmpty()) return object;

        ClassLoader classLoader = tClass.getClassLoader();
        Class<?>[] interfaces = tClass.getInterfaces();
        if (interfaces.length < 1) throw new RuntimeException(
                "невозможно использовать прокси конфигурирование для класса, не реализующего интерфейсы"
        );

        Object createdProxy = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method tClassMethod = tClass.getMethod(method.getName(), method.getParameterTypes());
                if (!tClassMethod.isAnnotationPresent(ReplaceMethod.class)
                        || tClassMethod.getAnnotation(ReplaceMethod.class).newMethodName().isEmpty()) {
                    return tClassMethod.invoke(object, args);
                } else {
                    Method newMethod = tClass.getMethod(
                            tClassMethod.getAnnotation(ReplaceMethod.class).newMethodName(),
                            tClassMethod.getParameterTypes()
                    );
                    return newMethod.invoke(object, args);
                }
            }
        });

        return (T) createdProxy;
    }

}
