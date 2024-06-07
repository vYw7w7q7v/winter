package di_engine.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static boolean checkIfClassImplementsIfc(Class<?> clazz, Class<?> ifc) {
        Class<?>[] interfaces = clazz.getInterfaces();

        for (Class<?> classImplementingIfc : interfaces) {
            if (ifc.getName().equals(classImplementingIfc.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Map<Class<?>, Collection<Class<?>>> getIfc2AllImplMap(Collection<Class<?>> classes) {
        Collection<Class<?>> interfaces = new LinkedList<>();
        Collection<Class<?>> notInterfaces = new LinkedList<>();

        for (Class<?> aClass : classes) {
            if (aClass.isInterface()) {
                interfaces.add(aClass);
            } else {
                notInterfaces.add(aClass);
            }
        }

        Map<Class<?>, Collection<Class<?>>> ifc2AllImpl = new HashMap<>();

        for (Class<?> anInterface : interfaces) {
            Collection<Class<?>> implementations = new LinkedList<>();
            for (Class<?> notInterface : notInterfaces) {
                if (checkIfClassImplementsIfc(notInterface, anInterface)) {
                    implementations.add(notInterface);
                }
            }
            ifc2AllImpl.put(anInterface, implementations);
        }
        return ifc2AllImpl;
    }
}
