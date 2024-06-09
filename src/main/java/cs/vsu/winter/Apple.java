package cs.vsu.winter;

import di_engine.custom.annotation.RandomInt;

public class Apple {

    @RandomInt(lowerBound = 0, upperBound = 10)
    double weight;
    public void drop() {
        System.out.printf("apple dropped! weight = %s%n", weight + "");
    }



}
