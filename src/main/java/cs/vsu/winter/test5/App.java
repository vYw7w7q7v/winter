package cs.vsu.winter.test5;

import di_engine.core.WinterApplication;

public class App
{
    public static void main(String[] args) {

        WinterApplication.run();

        TreeIfc tree = WinterApplication.getAppContext().createObject(TreeIfc.class);
        tree.shake();
    }

}
