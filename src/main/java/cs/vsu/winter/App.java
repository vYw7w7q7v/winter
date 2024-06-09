package cs.vsu.winter;

import di_engine.core.WinterApplication;

public class App
{
    public static void main( String[] args )
    {
        WinterApplication.run("src/main/resources/properties.wconf");
        TreeIfc tree = WinterApplication.getAppContext().createObject(TreeIfc.class);
        tree.drop();
    }
}
