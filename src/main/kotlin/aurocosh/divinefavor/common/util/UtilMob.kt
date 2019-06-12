package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.DivineFavor
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.*
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import java.util.HashSet

object UtilMob {
    private val witherClasses = HashSet<Class<out EntityLiving>>()
    private val hellClasses = HashSet<Class<out EntityLiving>>()

    init {
        witherClasses.add(EntityWither::class.java)
        witherClasses.add(EntityWitherSkeleton::class.java)

        hellClasses.add(EntityBlaze::class.java)
        hellClasses.add(EntityWitherSkeleton::class.java)

        val hell = ResourceLocation("hell")
        val biome = Biome.REGISTRY.getObject(hell)
        if (biome != null)
            EnumCreatureType.values().flatMap { biome.getSpawnableList(it) }.forEach { hellClasses.add(it.entityClass) }
        else
            DivineFavor.logger.error("Unable to access hell biom data to get list of hellish mob classes")
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
