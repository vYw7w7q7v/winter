package cs.vsu.winter;

import di_engine.custom.InjectByType;

public class Tree implements TreeIfc {

    @InjectByType
    public Apple apple;

    @Override
    public void drop() {
        apple.drop();
    }
}
