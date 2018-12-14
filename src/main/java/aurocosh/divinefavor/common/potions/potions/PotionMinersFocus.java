package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.base.PotionEffectCurse;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionMinersFocus extends ModPotionTrigger {
    private final int FATIGUE_DURATION = (int) (60 * 20 * 0.2);
    private final int FATIGUE_LEVEL = 3;

    public PotionMinersFocus() {
        super("miners_focus", true, 0x7FB8A4);
    }

    @Override
    public void trigger(EntityLivingBase player) {
        player.addPotionEffect(new PotionEffectCurse(MobEffects.MINING_FATIGUE, FATIGUE_DURATION, FATIGUE_LEVEL));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
