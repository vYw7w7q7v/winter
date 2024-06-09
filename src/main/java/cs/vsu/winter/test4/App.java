package cs.vsu.winter.test4;

import di_engine.core.WinterApplication;

public class App
{
    public static void main(String[] args) {
        WinterApplication.run();
        TreeIfc tree1 = WinterApplication.getAppContext().createObject(TreeIfc.class);
        TreeIfc tree2 = WinterApplication.getAppContext().createObject(TreeIfc.class);
    }

}
