package cs.vsu.winter.test4;

public class Apple implements AppleIfc {
    double weight = 5;
    public void drop() {
        System.out.printf("apple dropped! weight = %s\n", weight);
    }

}
