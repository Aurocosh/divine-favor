package aurocosh.divinefavor.common.item.bathing_blend;

import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.util.UtilTick;

public final class ModBathingBlends {
    private static ItemBathingBlend charcoal;
    private static ItemBathingBlend chilled_bonemeal;
    private static ItemBathingBlend ender_pearl;
    private static ItemBathingBlend feathers;
    private static ItemBathingBlend fleshy_blend;
    private static ItemBathingBlend redstone_blend;
    private static ItemBathingBlend wood;

    public static void preInit() {
        charcoal = new ItemBathingBlendModPotion("charcoal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.charred_aura, UtilTick.secondsToTicks(20));
        chilled_bonemeal = new ItemBathingBlendModPotion("chilled_bonemeal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.frosty_aura, UtilTick.secondsToTicks(20));
        ender_pearl = new ItemBathingBlendModPotion("ender_pearl", UtilTick.secondsToTicks(20), 35, ModBlendEffects.distorted_aura, UtilTick.secondsToTicks(20));
        feathers = new ItemBathingBlendModPotion("feathers", UtilTick.secondsToTicks(20), 35, ModBlendEffects.hunters_aura, UtilTick.secondsToTicks(20));
        fleshy_blend = new ItemBathingBlendModPotion("fleshy", UtilTick.secondsToTicks(20), 35, ModBlendEffects.visceral_aura, UtilTick.secondsToTicks(20));
        redstone_blend = new ItemBathingBlendModPotion("redstone", UtilTick.secondsToTicks(20), 35, ModBlendEffects.energetic_aura, UtilTick.secondsToTicks(20));
        wood = new ItemBathingBlendModPotion("wood", UtilTick.secondsToTicks(20), 35, ModBlendEffects.arboreal_aura, UtilTick.secondsToTicks(20));
    }
}