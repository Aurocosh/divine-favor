package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionWildCharge extends ModPotion {
    private final int SPEED_DURATION = UtilTick.minutesToTicks(1f);
    private final int SPEED_LEVEL = 4;
    private final float SLOWNESS_FORCE = 4;

    public PotionWildCharge() {
        super("wild_charge", true, 0x7FB8A4);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "817e7bc4-8ad5-4323-9131-aa71236a1b83", -SLOWNESS_FORCE, 2);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        livingBase.addPotionEffect(new ModEffect(ModPotions.wild_sprint, SPEED_DURATION, SPEED_LEVEL).setIsCurse());
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
