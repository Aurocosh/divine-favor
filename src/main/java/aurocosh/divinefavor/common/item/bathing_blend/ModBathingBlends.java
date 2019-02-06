package aurocosh.divinefavor.common.item.bathing_blend;

import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.util.UtilTick;

public final class ModBathingBlends {
    private static ItemBathingBlend charcoal;
    private static ItemBathingBlend wood;
    private static ItemBathingBlend chilled_bonemeal;
    private static ItemBathingBlend redstone_blend;

    public static void preInit() {
        charcoal = new ItemBathingBlendModPotion("charcoal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.charred_aura, UtilTick.secondsToTicks(20));
        wood = new ItemBathingBlendModPotion("wood", UtilTick.secondsToTicks(20), 35, ModBlendEffects.arboreal_aura, UtilTick.secondsToTicks(20));
        chilled_bonemeal = new ItemBathingBlendModPotion("chilled_bonemeal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.frosty_aura, UtilTick.secondsToTicks(20));
        redstone_blend = new ItemBathingBlendModPotion("redstone_blend", UtilTick.secondsToTicks(20), 35, ModBlendEffects.energetic_aura, UtilTick.secondsToTicks(20));
    }
}