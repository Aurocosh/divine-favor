package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionMinersFocus extends ModPotion {
    private final int FATIGUE_DURATION = (int) (60 * 20 * 0.2);
    private final int FATIGUE_LEVEL = 3;

    public PotionMinersFocus() {
        super("miners_focus", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        livingBase.addPotionEffect(new ModEffect(MobEffects.MINING_FATIGUE, FATIGUE_DURATION, FATIGUE_LEVEL).setIsCurse());
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
