package cs.vsu.winter.test1;

public class Tree implements TreeIfc {
    public AppleIfc apple;

    // добавлен setter
    public void setApple(AppleIfc apple) {
        this.apple = apple;
    }

    // добавлен конструктор с обязательным параметром
    public Tree(AppleIfc apple) {
        this.apple = apple;
    }

    @Override
    public void shake() {
        apple.drop();
    }

}
