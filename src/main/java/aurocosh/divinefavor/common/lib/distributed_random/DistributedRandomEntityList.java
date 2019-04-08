package aurocosh.divinefavor.common.lib.distributed_random;

import aurocosh.divinefavor.DivineFavor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import java.util.Map;

public class DistributedRandomEntityList extends DistributedRandomList<Class<? extends Entity>> {

    public DistributedRandomEntityList(Map<String, Double> entityTypeMap) {
        for (Map.Entry<String, Double> entry : entityTypeMap.entrySet()) {
            String entityName = entry.getKey();
            Class<? extends Entity> entityClass = EntityList.getClassFromName(entityName);
            if (entityClass != null)
                add(entityClass, entry.getValue());
            else
                DivineFavor.logger.error("Entity type not found: " + entityName);
        }
    }
}
