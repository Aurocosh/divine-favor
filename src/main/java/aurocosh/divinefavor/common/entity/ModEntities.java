package aurocosh.divinefavor.common.entity;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.entity.projectile.EntityCursedArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class ModEntities {
    public static void preInit() {
        int id = 0;
        registerModEntity(EntityStoneball.class, "stoneball", id++, DivineFavor.instance, 256, 10, true);
        registerModEntity(EntityCursedArrow.class, "cursed_arrow", id++, DivineFavor.instance, 256, 1, false);
    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation name = ResourceNamer.getFullName("entity",entityName);
        EntityRegistry.registerModEntity(name, entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}