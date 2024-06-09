package cs.vsu.winter.test6;

import cs.vsu.winter.utils.ColorPrint;
import di_engine.core.annotation.InitMethod;
import di_engine.core.annotation.InjectByType;
import di_engine.custom.annotation.ReplaceMethod;

public class Tree implements TreeIfc {

    @InjectByType
    public AppleIfc apple;

    @Override
    @ReplaceMethod(newMethodName = "newShake") // замена метода класса при помощи прокси
    public void shake() {
        apple.drop();
    }

    public void newShake() {
        ColorPrint.println("replaced shake method!", ColorPrint.AnsiColor.RED);
        apple.drop();
    }

    @InitMethod
    public void initTree() {
        ColorPrint.println("tree planted!", ColorPrint.AnsiColor.GREEN);
    }

}
