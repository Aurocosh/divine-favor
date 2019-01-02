package aurocosh.divinefavor.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySkeleton;

public class UtilMob {
    public static boolean isMobRanged(Entity entity){
        if(entity instanceof EntitySkeleton)
            return true;
        if(entity instanceof EntityBlaze)
            return true;
        if(entity instanceof EntityWither)
            return true;
        return false;
    }
}
