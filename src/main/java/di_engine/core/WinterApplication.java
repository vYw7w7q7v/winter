
package di_engine.core;

import di_engine.utils.ConfigParser;
import java.io.File;

public class WinterApplication {
    private static boolean appStarted = false;
    public static final String ENGINE_PATH = "src/main/java/di_engine";
    private static WinterContext appContext;

    private WinterApplication() {
    }

    public static WinterContext getAppContext() {
        if (!appStarted) {
            throw new RuntimeException("Инфраструктура DI не запущена!! Невозможно создать объект...");
        } else {
            return appContext;
        }
    }

    public static void run() {
        run("src/main/resources/properties.wconf");
    }


    public static void run(String configPath) {
        // создание конфигурационного файла
        Config config = ConfigParser.parseConfigFile(new File(configPath));

        // создание сканера пакетов
        Loader loader = new Loader(config);

        // инициализация контекста
        WinterContext context = new WinterContext(config, loader);

        // создание фабрики объектов
        ObjectFactory factory = new ObjectFactory(context, loader);
        context.setObjectFactory(factory);

        // добавление "неленивых" Singleton объектов в кеш контекста
        context.createAllNotLazySingletons2Cache();

        appContext = context;
        appStarted = true;
    }
}
