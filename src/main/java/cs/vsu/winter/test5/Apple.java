package cs.vsu.winter.test5;

import di_engine.custom.annotation.RandomInt;

public class Apple implements AppleIfc {

    // Аннотация @RandomInt, внедряющая случайное значение в поле
    @RandomInt(lowerBound = 1, upperBound = 7)
    int weight = 5;

    public void drop() {
        System.out.printf("apple dropped! weight = %s\n", weight);
    }

}
