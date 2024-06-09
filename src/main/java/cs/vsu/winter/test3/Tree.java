package cs.vsu.winter.test3;

import cs.vsu.winter.utils.ColorPrint;
import di_engine.core.annotation.InitMethod;
import di_engine.core.annotation.InjectByType;

public class Tree implements TreeIfc {

    @InjectByType
    public AppleIfc apple;

    @Override
    public void shake() {
        apple.drop();
    }

    // InitMethod, срабатывающий после конфигурирования объекта фабрикой
    @InitMethod
    public void initTree() {
        ColorPrint.println("tree planted!", ColorPrint.AnsiColor.GREEN);
    }

}
