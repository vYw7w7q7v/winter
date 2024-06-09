package cs.vsu.winter.test2;

import di_engine.core.annotation.InjectByType;

public class Tree implements TreeIfc {

    // добавлена аннотация @InjectByType для внедрения зависимости:
    // поле apple будет проинициализировано
    // из контекста инфраструктуры при создании объекта класса Tree
    @InjectByType
    public AppleIfc apple;

    @Override
    public void shake() {
        apple.drop();
    }

}
