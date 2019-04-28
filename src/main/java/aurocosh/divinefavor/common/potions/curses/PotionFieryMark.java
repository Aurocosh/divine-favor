package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class PotionFieryMark extends ModPotion {
    public PotionFieryMark() {
        super("fiery_mark", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onMobDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof EntityLivingBase))
            return;
        EntityLivingBase livingBase = (EntityLivingBase) entity;
        if (livingBase.isPotionActive(ModCurses.fiery_mark))
            explode(livingBase);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.isInWater())
            livingBase.removePotionEffect(ModCurses.fiery_mark);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        explode(livingBase);
    }

    private static void explode(EntityLivingBase livingBase) {
        if (livingBase.isInWater())
            return;
        livingBase.setFire(ConfigArrow.fieryMark.onFireSeconds);
        livingBase.attackEntityFrom(DamageSource.ON_FIRE, ConfigArrow.fieryMark.extraDamage);
        BlockPos pos = livingBase.getPosition();
        livingBase.world.newExplosion(livingBase, pos.getX(), pos.getY(), pos.getZ(), ConfigArrow.fieryMark.explosionPower, ConfigArrow.fieryMark.causeFire, ConfigArrow.fieryMark.damageTerrain);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
