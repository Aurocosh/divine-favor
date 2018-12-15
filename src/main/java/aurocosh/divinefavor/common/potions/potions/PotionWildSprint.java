package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.potions.base.effect.PotionEffectCurse;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionWildSprint extends ModPotionTrigger {
    private final int SPEED_DURATION = UtilTick.minutesToTicks(1f);
    private final int SPEED_LEVEL = 4;

    public PotionWildSprint() {
        super("wild_sprint", true, 0x7FB8A4);
    }

    @Override
    public void trigger(EntityLivingBase player) {
        player.addPotionEffect(new PotionEffectCurse(MobEffects.SPEED, SPEED_DURATION, SPEED_LEVEL));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
