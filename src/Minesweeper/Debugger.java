package Minesweeper;

public class Debugger {
    public static boolean showInfoDebug = true;
    public static boolean showWarningDebug = true;
    public static boolean showSevereDebug = true;

    public static void info(Object o){
        if(showInfoDebug) System.out.println(o.toString());
    }
    public static void warning(Object o){
        if(showWarningDebug) System.out.println(o.toString());
    }
    public static void severe(Object o){
        if(showSevereDebug) System.out.println(o.toString());
    }
}
