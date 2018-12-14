package aurocosh.divinefavor.common.potions.base.potion;

import net.minecraft.entity.EntityLivingBase;

public abstract class ModPotionTrigger extends ModPotion {
    public ModPotionTrigger(String name, boolean beneficial, int potionColor) {
        super(name, beneficial, potionColor);
    }

    public abstract void trigger(EntityLivingBase player);

}
