package aurocosh.divinefavor.common.util

import net.minecraft.entity.Entity
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.monster.EntitySkeleton

object UtilMob {
    fun isMobRanged(entity: Entity): Boolean {
        if (entity is EntitySkeleton)
            return true
        if (entity is EntityBlaze)
            return true
        return entity is EntityWither
    }
}
