package aurocosh.divinefavor.common.util;

import net.minecraft.util.math.MathHelper;

public class UtilArray {
    public static <T> int clampIndex(T[] array, int index) {
        return MathHelper.clamp(index, 0, array.length - 1);
    }
}
