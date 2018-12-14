package aurocosh.divinefavor.common.entity.common;

import net.minecraft.entity.monster.IMob;

import java.util.HashMap;
import java.util.Map;

public class MobGrudgeRecognizer {
    public static Map<Integer, Class> mobs = new HashMap<>();
    public static Map<Integer, String> mobNames = new HashMap<>();
    private static int nextId = 0;

    public static void registerMob(Class clazz, String name) {
        mobs.put(nextId, clazz);
        mobNames.put(nextId++, name);
    }

    public static int getMobType(IMob mob) {
        for (Map.Entry<Integer, Class> entry : mobs.entrySet()) {
            if (entry.getValue().isInstance(mob))
                return entry.getKey();
        }
        return -1;
    }

    public static boolean checkMobType(int typeId, IMob mob) {
        Class clazz = mobs.get(typeId);
        if (clazz == null)
            return false;
        return clazz.isInstance(mob);
    }

    public static String getMobName(int typeId) {
        return mobNames.computeIfAbsent(typeId, k -> "None");
    }
}
