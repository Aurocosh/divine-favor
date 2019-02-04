package aurocosh.divinefavor.common.item.bathing_blend.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemBathingBlendModPotion extends ItemBathingBlendSimple {
    private final ModPotion potion;
    private final int addedDuration;

    public ItemBathingBlendModPotion(String name, int baseDuration, float basePotency, ModPotion potion, int addedDuration) {
        super(name, baseDuration, basePotency);
        this.potion = potion;
        this.addedDuration = addedDuration;
    }

    public void applyEffect(EntityLivingBase livingBase) {
        int duration = addedDuration;
        PotionEffect potionEffect = livingBase.getActivePotionMap().get(potion);
        if (potionEffect != null)
            duration += potionEffect.getDuration();
        livingBase.addPotionEffect(new ModEffect(potion, duration));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}