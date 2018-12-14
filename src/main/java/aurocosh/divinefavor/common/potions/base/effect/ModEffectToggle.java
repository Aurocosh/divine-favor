package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class ModEffectToggle extends ModEffect {
    private boolean active;
    public ModEffectToggle(ModPotionToggle potion) {
        super(potion);
        active = true;
    }

    @Override
    public boolean onUpdate(EntityLivingBase entityIn) {
        return active;
    }

    @Override
    public void combine(PotionEffect other) {
        active = false;
    }
}