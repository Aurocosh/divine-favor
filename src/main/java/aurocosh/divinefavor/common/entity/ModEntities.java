package aurocosh.divinefavor.common.entity;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstEntityNames;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class ModEntities {
    public static void preInit() {
        int id = 0;
        registerModEntity(EntityStoneball.class, ConstEntityNames.STONEBALL, id++, DivineFavor.instance, 256, 10, true);
    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(new ResourceLocation(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}