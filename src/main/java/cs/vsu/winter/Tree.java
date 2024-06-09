package cs.vsu.winter;

import di_engine.core.annotation.InitMethod;
import di_engine.core.annotation.InjectByType;

public class Tree implements TreeIfc {

    @InjectByType
    public Apple apple;

    @Override
    public void drop() {
        apple.drop();
    }

    @InitMethod
    public void init() {
        System.out.println("Tree init method started!");
    }
}
