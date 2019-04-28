package aurocosh.divinefavor.common.lib.distributed_random

import aurocosh.divinefavor.DivineFavor
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityList

class DistributedRandomEntityList(entityTypeMap: Map<String, Double>) : DistributedRandomList<Class<out Entity>>() {

    init {
        for ((entityName, value) in entityTypeMap) {
            val entityClass = EntityList.getClassFromName(entityName)
            if (entityClass != null)
                add(entityClass, value)
            else
                DivineFavor.logger.error("Entity type not found: $entityName")
        }
    }
}
