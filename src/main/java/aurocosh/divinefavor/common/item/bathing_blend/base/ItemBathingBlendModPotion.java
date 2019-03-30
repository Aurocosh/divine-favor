package aurocosh.divinefavor.common.item.bathing_blend.base;

import aurocosh.divinefavor.common.config.items.BathingBlendPotion;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemBathingBlendModPotion extends ItemBathingBlendSimple {
    private final ModPotion potion;
    private final int extraPotionDuration;

    public ItemBathingBlendModPotion(String name, ModPotion potion, BathingBlendPotion settings) {
        this(name, settings.duration, settings.rate, potion, settings.extraPotionDuration);
    }

    public ItemBathingBlendModPotion(String name, int duration, int rate, ModPotion potion, int extraPotionDuration) {
        super(name, duration, rate);
        this.potion = potion;
        this.extraPotionDuration = extraPotionDuration;
    }

    public void applyEffect(EntityLivingBase livingBase) {
        int duration = extraPotionDuration;
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