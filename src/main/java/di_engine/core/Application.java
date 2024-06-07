
package di_engine.core;

import di_engine.utils.ConfigParser;
import java.io.File;

public class Application {
    private static boolean appStarted = false;
    public static final String ENGINE_PATH = "src/main/java/di_engine";
    private static WinterContext appContext;

    private Application() {
    }

    public static WinterContext getAppContext() {
        if (!appStarted) {
            throw new RuntimeException("Инфраструктура DI не запущена!! Невозможно создать объект...");
        } else {
            return appContext;
        }
    }

    public static void run(String configPath) {
        Config config = ConfigParser.parseConfigFile(new File(configPath));
        Loader loader = new Loader(config);
        WinterContext context = new WinterContext(config, loader);
        ObjectFactory factory = new ObjectFactory(context, loader);
        context.setObjectFactory(factory);
        context.createAllNotLazySingletons2Cache();
        appContext = context;
        appStarted = true;
    }
}
