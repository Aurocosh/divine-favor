package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.lib.LimitedTimer;
import aurocosh.divinefavor.common.lib.PrivateField;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionArrowDeflection extends ModPotion {
    private static final LimitedTimer COOLDOWN_COUNTER = new LimitedTimer(ConfigSpells.arrowDeflection.deflectionCooldown);
    private final static float RADIUS_SQ = ConfigSpells.arrowDeflection.radius * ConfigSpells.arrowDeflection.radius;
    private final static PrivateField<EntityArrow, Boolean> IN_GROUND = new PrivateField<>(EntityArrow.class, 7, false);

    public PotionArrowDeflection() {
        super("arrow_deflection", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (!COOLDOWN_COUNTER.tick())
            return;

        List<EntityArrow> entities = livingBase.world.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(livingBase.getPosition()).grow(ConfigSpells.arrowDeflection.radius));
        List<EntityArrow> projectiles = UtilList.select(entities, element -> !IN_GROUND.get(element) && (element.getDistanceSq(livingBase) <= RADIUS_SQ));
        for (Entity projectile : projectiles) {
            Vec3d directionToTarget = livingBase.getPositionVector().subtract(projectile.getPositionVector()).normalize();
            Vec3d motionVector = UtilEntity.getMotionVector(projectile);
            Vec3d motionDirection = motionVector.normalize();
            double product = directionToTarget.dotProduct(motionDirection);
            if (product > ConfigSpells.arrowDeflection.tolerance)
                UtilEntity.setVelocity(projectile, (motionDirection.scale(-1)), (float) motionVector.length());
            COOLDOWN_COUNTER.reset();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityDamaged(LivingDamageEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        if (!livingBase.isPotionActive(ModPotions.arrow_deflection))
            return;
        DamageSource source = event.getSource();
        if (source instanceof EntityDamageSourceIndirect && ((EntityDamageSourceIndirect) source).damageType.equals("arrow"))
            return;

        int duration = -ConfigSpells.arrowDeflection.durationDecrease;
        PotionEffect potionEffect = livingBase.getActivePotionMap().get(ModPotions.arrow_deflection);
        if (potionEffect != null)
            duration += potionEffect.getDuration();
        livingBase.removePotionEffect(ModPotions.arrow_deflection);
        livingBase.addPotionEffect(new ModEffect(ModPotions.arrow_deflection, duration));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
