package cs.vsu.winter.test1;

public class App
{
    public static void main(String[] args) {
        AppleIfc apple = new Apple();
        TreeIfc tree = new Tree(apple);
        tree.shake();
    }
}
