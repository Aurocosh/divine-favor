package aurocosh.divinefavor.common.util;

import java.util.Random;

public class UtilRandom {
    public static Random random = new Random();

    public static boolean getPercentChance(float chance)
    {
        float value = random.nextFloat() * 100;
        return value < chance;
    }

    public static int nextInt(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    public static int nextIntExclusive(int min, int max){
        return random.nextInt(max - min) + min;
    }
}
