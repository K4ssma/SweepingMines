package DebugPackage;

public class Debugger {
    private final static DebugLevel currentDebugLevel = DebugLevel.INFO;

    public static void info(Object o){
        if(currentDebugLevel == DebugLevel.INFO){
            System.out.println(o.toString());
        }
    }
    public static void warning(Object o){
        if(currentDebugLevel == DebugLevel.INFO || currentDebugLevel == DebugLevel.WARNING){
            System.out.println(o.toString());
        }
    }
    public static void severe(Object o){
        if(currentDebugLevel == DebugLevel.INFO || currentDebugLevel == DebugLevel.WARNING || currentDebugLevel == DebugLevel.SEVERE){
            System.out.println(o.toString());
        }
    }
}
