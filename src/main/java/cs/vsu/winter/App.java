package cs.vsu.winter;

import di_engine.core.Application;

public class App
{
    public static void main( String[] args )
    {
        Application.run("src/main/resources/properties.wconf");
        TreeIfc tree = Application.getAppContext().createObject(TreeIfc.class);
        tree.drop();
    }
}
