package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class ModEffectTrigger extends ModEffect {
    public ModEffectTrigger(ModPotionTrigger potion, int duration) {
        super(potion, duration);
    }

    @Override
    public boolean onUpdate(EntityLivingBase entityIn) {
        if (getDuration() > 1)
            return super.onUpdate(entityIn);

        Potion potion = getPotion();
        if(potion instanceof ModPotionTrigger){
            ModPotionTrigger trigger = (ModPotionTrigger) potion;
            trigger.trigger(entityIn);
        }
        return false;
    }
}