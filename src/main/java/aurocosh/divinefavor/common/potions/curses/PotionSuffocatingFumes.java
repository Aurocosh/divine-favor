package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData;
import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionSuffocatingFumes extends ModPotion {
    public static final float DAMAGE = 2f;
    public static final int HEIGHT_TO_CLIMB = 15;
    public static final int DAMAGE_RATE = UtilTick.secondsToTicks(3);
    public static final int CURE_RATE = UtilTick.secondsToTicks(1);

    private static final TickCounter DAMAGE_COUNTER = new TickCounter(DAMAGE_RATE);
    private static final TickCounter CURE_COUNTER = new TickCounter(CURE_RATE);

    public PotionSuffocatingFumes() {
        super("suffocating_fumes", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);

        SuffocatingFumesData fumesData = LivingData.get(livingBase).getSuffocatingFumesData();
        fumesData.setY(livingBase.getPosition().getY());
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if(DAMAGE_COUNTER.isFinished())
            livingBase.attackEntityFrom(DamageSource.DROWN, DAMAGE);
        if(CURE_COUNTER.isFinished() && isCured(livingBase))
            livingBase.removePotionEffect(ModCurses.suffocating_fumes);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    private boolean isCured(EntityLivingBase livingBase) {
        SuffocatingFumesData fumesData = LivingData.get(livingBase).getSuffocatingFumesData();
        int oldY = fumesData.getY();
        int newY = livingBase.getPosition().getY();
        return newY - oldY > HEIGHT_TO_CLIMB;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        DAMAGE_COUNTER.tick();
        CURE_COUNTER.tick();
    }
}
