package aurocosh.divinefavor.common.item.bathing_blend;

import aurocosh.divinefavor.common.config.common.ConfigItem;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;

public final class ModBathingBlends {
    private static ItemBathingBlend charcoal;
    private static ItemBathingBlend ender_pearl;
    private static ItemBathingBlend feathers;
    private static ItemBathingBlend fleshy;
    private static ItemBathingBlend flint;
    private static ItemBathingBlend lapis;
    private static ItemBathingBlend redstone;
    private static ItemBathingBlend snow;
    private static ItemBathingBlend wood;

    public static void preInit() {
        charcoal = new ItemBathingBlendModPotion("charcoal", ModBlendEffects.charred_aura, ConfigItem.blendCharcoal);
        ender_pearl = new ItemBathingBlendModPotion("ender_pearl", ModBlendEffects.distorted_aura, ConfigItem.blendEnderPearl);
        feathers = new ItemBathingBlendModPotion("feathers", ModBlendEffects.hunters_aura, ConfigItem.blendFeathers);
        fleshy = new ItemBathingBlendModPotion("fleshy", ModBlendEffects.visceral_aura, ConfigItem.blendFleshy);
        flint = new ItemBathingBlendModPotion("flint", ModBlendEffects.mineral_aura, ConfigItem.blendFlint);
        lapis = new ItemBathingBlendModPotion("lapis", ModBlendEffects.calling_aura, ConfigItem.blendLapis);
        redstone = new ItemBathingBlendModPotion("redstone", ModBlendEffects.energetic_aura, ConfigItem.blendRedstone);
        snow = new ItemBathingBlendModPotion("snow", ModBlendEffects.frosty_aura, ConfigItem.blendSnow);
        wood = new ItemBathingBlendModPotion("wood", ModBlendEffects.arboreal_aura, ConfigItem.blendWood);
    }
}