package cs.vsu.winter.test0;

public class Tree implements TreeIfc {
    public AppleIfc apple = new Apple();

    @Override
    public void shake() {
        apple.drop();
    }

}
