package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.custom_data.living.LivingData;
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesData;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionSuffocatingFumes extends ModPotion {
    private static final LoopedCounter DAMAGE_COUNTER = new LoopedCounter(ConfigArrow.suffocatingFumes.damageRate);
    private static final LoopedCounter CURE_COUNTER = new LoopedCounter(ConfigArrow.suffocatingFumes.cureRate);

    public PotionSuffocatingFumes() {
        super("suffocating_fumes", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);

        SuffocatingFumesData fumesData = LivingData.get(livingBase).getSuffocatingFumesData();
        fumesData.setY(livingBase.getPosition().getY());
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        if (livingBase.world.isRemote)
            return;
        if (DAMAGE_COUNTER.isFinished())
            livingBase.attackEntityFrom(DamageSource.DROWN, ConfigArrow.suffocatingFumes.damage);
        if (CURE_COUNTER.isFinished() && isCured(livingBase))
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
        return newY - oldY > ConfigArrow.suffocatingFumes.heightToClimb;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        DAMAGE_COUNTER.tick();
        CURE_COUNTER.tick();
    }
}
