package cs.vsu.winter.test3;

import di_engine.core.WinterApplication;

public class App
{
    public static void main(String[] args) {
        // запуск инфраструктуры
        // можно не указывать конфиг файл, тогда он будет найден при сканировании
        WinterApplication.run();

        TreeIfc tree = WinterApplication.getAppContext().createObject(TreeIfc.class);
        tree.shake();
    }

}
