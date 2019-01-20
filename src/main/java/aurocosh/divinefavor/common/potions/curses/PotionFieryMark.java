package aurocosh.divinefavor.common.potions.curses;

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

@Mod.EventBusSubscriber
public class PotionFieryMark extends ModPotion {
    private static final float EXPLOSION_POWER = 4;
    private static final boolean CAUSE_FIRE = false;
    private static final boolean DAMAGE_TERRAIN = true;
    private static final int FIRE_SECONDS = 3;
    public static float EXTRA_DAMAGE = 20;

    public PotionFieryMark() {
        super("fiery_mark", true, 0x7FB8A4);
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
        if(livingBase.isInWater())
            livingBase.removePotionEffect(ModCurses.fiery_mark);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        explode(livingBase);
    }

    private static void explode(EntityLivingBase livingBase) {
        if(livingBase.isInWater())
            return;
        livingBase.setFire(FIRE_SECONDS);
        livingBase.attackEntityFrom(DamageSource.ON_FIRE, EXTRA_DAMAGE);
        BlockPos pos = livingBase.getPosition();
        livingBase.world.newExplosion(livingBase, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, CAUSE_FIRE, DAMAGE_TERRAIN);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
