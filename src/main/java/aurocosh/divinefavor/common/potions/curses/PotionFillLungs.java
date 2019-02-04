package aurocosh.divinefavor.common.potions.curses;

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
public class PotionFillLungs extends ModPotion {
    public static final float DAMAGE = 2f;
    public static final int DROWNING_RATE = UtilTick.secondsToTicks(3);
    private static final TickCounter COUNTER = new TickCounter(DROWNING_RATE);

    public PotionFillLungs() {
        super("fill_lungs", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if(livingBase.world.isRemote)
            return;
        if(livingBase.isInWater())
            livingBase.removePotionEffect(ModCurses.fill_lungs);
        else if(COUNTER.isFinished())
            livingBase.attackEntityFrom(DamageSource.DROWN, DAMAGE);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            COUNTER.tick();
    }
}
