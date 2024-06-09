package cs.vsu.winter.utils;


public class ColorPrint {

    public enum AnsiColor {

        DEFAULT("39"),

        BLACK("30"),

        RED("31"),

        GREEN("32"),

        YELLOW("33"),

        BLUE("34"),

        MAGENTA("35"),

        CYAN("36"),

        WHITE("37"),

        BRIGHT_BLACK("90"),

        BRIGHT_RED("91"),

        BRIGHT_GREEN("92"),

        BRIGHT_YELLOW("93"),

        BRIGHT_BLUE("94"),

        BRIGHT_MAGENTA("95"),

        BRIGHT_CYAN("96"),

        BRIGHT_WHITE("97");

        private final String code;

        AnsiColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private static final String ANSI_RESET = "\u001B[0m";

    private ColorPrint() {
    }

    public static void println(String string, AnsiColor color) {
        System.out.println("\u001B[" + color.toString() + "m" + string + "\u001B[0m");
    }
}
