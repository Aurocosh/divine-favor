package aurocosh.divinefavor.common.item.bathing_blend;

import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.util.UtilTick;

public final class ModBathingBlends {
    private static ItemBathingBlend charcoal;
    private static ItemBathingBlend ender_pearl;
    private static ItemBathingBlend feathers;
    private static ItemBathingBlend fleshy_blend;
    private static ItemBathingBlend flint;
    private static ItemBathingBlend lapis;
    private static ItemBathingBlend redstone_blend;
    private static ItemBathingBlend snow;
    private static ItemBathingBlend wood;

    public static void preInit() {
        charcoal = new ItemBathingBlendModPotion("charcoal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.charred_aura, UtilTick.secondsToTicks(20));
        ender_pearl = new ItemBathingBlendModPotion("ender_pearl", UtilTick.secondsToTicks(20), 35, ModBlendEffects.distorted_aura, UtilTick.secondsToTicks(20));
        feathers = new ItemBathingBlendModPotion("feathers", UtilTick.secondsToTicks(20), 35, ModBlendEffects.hunters_aura, UtilTick.secondsToTicks(20));
        fleshy_blend = new ItemBathingBlendModPotion("fleshy", UtilTick.secondsToTicks(20), 35, ModBlendEffects.visceral_aura, UtilTick.secondsToTicks(20));
        flint = new ItemBathingBlendModPotion("flint", UtilTick.secondsToTicks(20), 35, ModBlendEffects.mineral_aura, UtilTick.secondsToTicks(20));
        lapis = new ItemBathingBlendModPotion("lapis", UtilTick.secondsToTicks(20), 35, ModBlendEffects.calling_aura, UtilTick.secondsToTicks(20));
        redstone_blend = new ItemBathingBlendModPotion("redstone", UtilTick.secondsToTicks(20), 35, ModBlendEffects.energetic_aura, UtilTick.secondsToTicks(20));
        snow = new ItemBathingBlendModPotion("snow", UtilTick.secondsToTicks(20), 35, ModBlendEffects.frosty_aura, UtilTick.secondsToTicks(20));
        wood = new ItemBathingBlendModPotion("wood", UtilTick.secondsToTicks(20), 35, ModBlendEffects.arboreal_aura, UtilTick.secondsToTicks(20));
    }
}