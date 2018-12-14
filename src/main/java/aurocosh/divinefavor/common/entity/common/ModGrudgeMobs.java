package aurocosh.divinefavor.common.entity.common;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;

public class ModGrudgeMobs {
    public static void preInit() {
        MobGrudgeRecognizer.registerMob(EntityBlaze.class, "entity.Blaze.name");
        MobGrudgeRecognizer.registerMob(EntityCreeper.class, "entity.Creeper.name");
        MobGrudgeRecognizer.registerMob(EntityDragon.class, "entity.EnderDragon.name");
        MobGrudgeRecognizer.registerMob(EntityEnderman.class, "entity.Enderman.name");
        MobGrudgeRecognizer.registerMob(EntityGhast.class, "entity.Ghast.name");
        MobGrudgeRecognizer.registerMob(EntityGuardian.class, "entity.Guardian.name");
        MobGrudgeRecognizer.registerMob(EntityShulker.class, "entity.Shulker.name");
        MobGrudgeRecognizer.registerMob(EntitySilverfish.class, "entity.Silverfish.name");
        MobGrudgeRecognizer.registerMob(EntitySkeleton.class, "entity.Skeleton.name");
        MobGrudgeRecognizer.registerMob(EntitySlime.class, "entity.Slime.name");
        MobGrudgeRecognizer.registerMob(EntitySpider.class, "entity.Spider.name");
        MobGrudgeRecognizer.registerMob(EntityWitch.class, "entity.Witch.name");
        MobGrudgeRecognizer.registerMob(EntityWither.class, "entity.WitherSkeleton.name");
        MobGrudgeRecognizer.registerMob(EntityWitherSkeleton.class, "entity.WitherSkeleton.name");
        MobGrudgeRecognizer.registerMob(EntityZombie.class, "entity.Zombie.name");
        MobGrudgeRecognizer.registerMob(EntityPigZombie.class, "entity.PigZombie.name");
    }
}
