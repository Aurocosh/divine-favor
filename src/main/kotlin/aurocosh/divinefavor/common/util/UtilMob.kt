package aurocosh.divinefavor.common.util

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.monster.EntityWitherSkeleton
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import java.util.HashSet

object UtilMob {
    private val witherClasses = HashSet<Class<out EntityLiving>>()
    private val hellClasses = HashSet<Class<out EntityLiving>>()

    init {
        witherClasses.add(EntityWither::class.java)
        witherClasses.add(EntityWitherSkeleton::class.java)

        val hell = ResourceLocation("hell")
        for (creatureType in EnumCreatureType.values())
            for (spawnListEntry in Biome.REGISTRY.getObject(hell)!!.getSpawnableList(creatureType))
                hellClasses.add(spawnListEntry.entityClass)
    }

    fun isMobRanged(entity: Entity): Boolean {
        if (entity is EntitySkeleton)
            return true
        if (entity is EntityBlaze)
            return true
        return entity is EntityWither
    }

    fun isMobHellish(entity: Entity): Boolean {
        return hellClasses.contains(entity::class.java)
    }

    fun isMobWithering(entity: Entity): Boolean {
        return witherClasses.contains(entity::class.java)
    }
}
