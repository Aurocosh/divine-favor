package aurocosh.divinefavor.common.util;

public class UtilColor {
    public static int getColor(int alpha, int red, int green, int blue) {
        return  (red) + (green << 8) + (blue << 16) + (alpha << 24);
    }
}
