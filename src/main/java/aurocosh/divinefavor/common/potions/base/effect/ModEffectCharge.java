package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ModEffectCharge extends ModEffect {
    public ModEffectCharge(ModPotionCharge potion, int charges) {
        super(potion, 0, charges);
    }

    public int consumeCharge() {
        int charges = getAmplifier() - 1;
        ReflectionHelper.setPrivateValue(PotionEffect.class, this, charges, 3);
        return charges;
    }

    @Override
    public void combine(PotionEffect other) {
        if (this.modPotion != other.getPotion())
            DivineFavor.logger.info("This method should only be called for matching effects!");
//        int amplifier = other.getAmplifier();
//        if (amplifier > this.getAmplifier())
//            ReflectionHelper.setPrivateValue(PotionEffect.class, this, amplifier, 3);
        int charges = Math.max(other.getAmplifier(), this.getAmplifier());
        ReflectionHelper.setPrivateValue(PotionEffect.class, this, charges, 3);
    }

    @Override
    public boolean onUpdate(EntityLivingBase entityIn) {
        return getAmplifier() > 0;
    }

}