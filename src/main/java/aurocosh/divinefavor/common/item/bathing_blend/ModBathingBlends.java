package aurocosh.divinefavor.common.item.bathing_blend;

import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlendModPotion;
import aurocosh.divinefavor.common.potions.common.ModBlendEffects;
import aurocosh.divinefavor.common.util.UtilTick;

import java.util.ArrayList;
import java.util.List;

public final class ModBathingBlends {
    public static final List<ItemBathingBlend> BATHING_BLENDS = new ArrayList<>();

    public static void preInit() {
        BATHING_BLENDS.add(new ItemBathingBlendModPotion("charcoal", UtilTick.secondsToTicks(20), 35, ModBlendEffects.charred_aura, UtilTick.secondsToTicks(20)));
        BATHING_BLENDS.add(new ItemBathingBlendModPotion("wood", UtilTick.secondsToTicks(20), 35, ModBlendEffects.arboreal_aura, UtilTick.secondsToTicks(20)));
    }
}