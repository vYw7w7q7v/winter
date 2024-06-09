package cs.vsu.winter.test4;

import cs.vsu.winter.utils.ColorPrint;
import di_engine.core.annotation.InitMethod;
import di_engine.core.annotation.InjectByType;
import di_engine.core.annotation.Singleton;

// Добавлена аннотация @Singleton, обозначающая,
// что объект должен существовать только в одном экземпляре
@Singleton
public class Tree implements TreeIfc {

    @InjectByType
    public AppleIfc apple;

    @Override
    public void shake() {
        apple.drop();
    }

    @InitMethod
    public void initTree() {
        ColorPrint.println("tree planted!", ColorPrint.AnsiColor.GREEN);
    }

}
