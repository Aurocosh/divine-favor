package aurocosh.divinefavor.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UtilRandom {
    public static Random random = new Random();

    public static boolean rollDiceFloat(float chance) {
        float value = random.nextFloat();
        return value < chance;
    }

    public static boolean rollDice(float chance) {
        float value = random.nextFloat() * 100;
        return value < chance;
    }

    public static int nextInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static double nextDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static int nextIntExclusive(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static <T> List<T> selectRandom(List<T> list, int count) {
        int size = list.size();
        count = UtilMath.clamp(count, 0, size);
        int dirsToRemove = size - count;

        List<T> listShallowCopy = new ArrayList<>(list);
        for (int i = 0; i < dirsToRemove; i++) {
            int index = nextIntExclusive(0, listShallowCopy.size());
            listShallowCopy.remove(index);
        }
        return listShallowCopy;
    }

    public static int getRandomIndex(List list) {
        return nextInt(0, list.size() - 1);
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(getRandomIndex(list));
    }
}
