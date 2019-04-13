package aurocosh.divinefavor.common.entity;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityStoneball;
import aurocosh.divinefavor.common.entity.rope.EntityExplosiveChargeNode;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class ModEntities {
    public static void preInit() {
        int id = 0;
        registerModEntity(EntityStoneball.class, "stoneball", id++, DivineFavor.instance, 256, 10, true);
        registerModEntity(EntitySpellArrow.class, "spell_arrow", id++, DivineFavor.instance, 256, 1, true);

        registerModEntity(MinionZombie.class, "minion_zombie", id++, DivineFavor.instance,80, 3, false);
        registerModEntity(MinionHusk.class, "minion_husk", id++, DivineFavor.instance,80, 3, false);
        registerModEntity(MinionSkeleton.class, "minion_skeleton", id++, DivineFavor.instance,80, 3, false);
        registerModEntity(MinionStray.class, "minion_stray", id++, DivineFavor.instance,80, 3, false);
        registerModEntity(MinionCreeper.class, "minion_creeper", id++, DivineFavor.instance,80, 3, false);

        registerModEntity(EntityDirewolf.class, "direwolf", id++, DivineFavor.instance,80, 3, false);
        registerModEntity(EntityExplosiveChargeNode.class, "explosive_charge", id++, DivineFavor.instance,64, 1, true);
    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation name = ResourceNamer.getFullName("entity",entityName);
        EntityRegistry.registerModEntity(name, entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}