package cs.vsu.winter;

import di_engine.core.annotation.InitMethod;
import di_engine.core.annotation.InjectByType;
import di_engine.custom.annotation.ReplaceMethod;

public class Tree implements TreeIfc {

    @InjectByType
    public Apple apple;

    @Override
    @ReplaceMethod(newMethodName = "newDrop")
    public void drop() {
        apple.drop();
    }

    @InitMethod
    public void init() {
        System.out.println("Tree init method started!");
    }

    public void newDrop() {
        System.out.println("replaced method!!!");
    }
}
