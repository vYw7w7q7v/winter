package cs.vsu.winter.test2;

import di_engine.core.WinterApplication;

public class App
{
    public static void main(String[] args) {
        // запуск инфраструктуры
        WinterApplication.run("src/main/resources/properties.wconf");

        TreeIfc tree = WinterApplication.getAppContext().createObject(TreeIfc.class);
        tree.shake();
    }

}
