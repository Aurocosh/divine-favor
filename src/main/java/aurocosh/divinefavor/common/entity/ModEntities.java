package aurocosh.divinefavor.common.entity;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.entity.mob.EntityDirewolf;
import aurocosh.divinefavor.common.entity.projectile.*;
import aurocosh.divinefavor.common.entity.rope.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class ModEntities {
    private static int nextId = 0;

    public static void preInit() {
        registerModEntity(EntityStoneball.class, "stoneball", 256, 10, true);
        registerModEntity(EntitySpellArrow.class, "spell_arrow", 256, 1, true);
        registerModEntity(EntityClimbingArrow.class, "climbing_arrow", 256, 1, true);
        registerModEntity(EntityVacuumArrow.class, "vacuum_arrow", 256, 1, true);
        registerModEntity(EntitySniperArrow.class, "sniper_arrow", 256, 1, true);
        registerModEntity(EntityStasisArrow.class, "stasis_arrow", 256, 1, true);

        registerModEntity(MinionZombie.class, "minion_zombie", 80, 3, false);
        registerModEntity(MinionHusk.class, "minion_husk", 80, 3, false);
        registerModEntity(MinionSkeleton.class, "minion_skeleton", 80, 3, false);
        registerModEntity(MinionStray.class, "minion_stray", 80, 3, false);
        registerModEntity(MinionCreeper.class, "minion_creeper", 80, 3, false);

        registerModEntity(EntityDirewolf.class, "direwolf", 80, 3, false);

        registerModEntity(EntityRopeBarrierNode.class, "rope_barrier", 64, 1, true);
        registerModEntity(EntityRopeExplosiveNode.class, "rope_explosive", 64, 1, true);
        registerModEntity(EntityRopeGlowingNode.class, "rope_glowing", 64, 1, true);
        registerModEntity(EntityRopeGuideNode.class, "rope_guide", 64, 1, true);
        registerModEntity(EntityRopeInertNode.class, "rope_inert", 64, 1, true);
        registerModEntity(EntityRopeTeleportingNode.class, "rope_teleporting", 64, 1, true);
    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation name = ResourceNamer.getFullName("entity", entityName);
        EntityRegistry.registerModEntity(name, entityClass, entityName, nextId++, DivineFavor.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}