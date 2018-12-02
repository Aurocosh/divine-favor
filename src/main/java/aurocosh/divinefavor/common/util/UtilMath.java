package aurocosh.divinefavor.common.util;

public class UtilMath {
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
