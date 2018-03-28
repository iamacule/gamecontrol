package vn.mran.gamecontrol.util;

/**
 * Created by Mr An on 01/12/2017.
 */

public class Util {
    public static boolean checkNull(String value) {
        if (value==null)
            return true;
        if (value.equals(""))
            return true;
        return false;
    }

    public static int convertStringToInt(String value){
        return Integer.parseInt(value);
    }
}
