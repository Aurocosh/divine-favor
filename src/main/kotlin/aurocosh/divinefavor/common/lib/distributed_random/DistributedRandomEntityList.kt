package aurocosh.divinefavor.common.lib.distributed_random

import aurocosh.divinefavor.DivineFavor
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

class DistributedRandomEntityList(entityTypeMap: Map<String, Double>) : DistributedRandomList<Class<out Entity>>() {

    init {
        for ((entityName, value) in entityTypeMap) {
            val entityClass = getClassFromName(entityName)
            if (entityClass != null)
                add(entityClass, value)
            else
                DivineFavor.logger.error("Entity type not found: $entityName")
        }
    }

    companion object {
        fun getClassFromName(p_192839_0_: String): Class<out Entity>? {
            val entry = net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES.getValue(ResourceLocation(p_192839_0_))
            return entry?.entityClass
        }
    }
}
