package aurocosh.divinefavor.common.util;

import java.util.Random;

public class UtilRandom {
    private static Random random = new Random();

    public static boolean getPercentChance(float chance)
    {
        float value = random.nextFloat() * 100;
        return value < chance;
    }
}
