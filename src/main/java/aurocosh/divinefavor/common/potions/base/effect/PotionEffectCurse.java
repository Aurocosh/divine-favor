package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collections;

public class PotionEffectCurse extends PotionEffect {
    public PotionEffectCurse(Potion potion) {
        super(potion);
        setCurativeItems(new ArrayList<>());
    }

    public PotionEffectCurse(Potion potion, int durationIn) {
        super(potion, durationIn);
        setCurativeItems(new ArrayList<>());
    }

    public PotionEffectCurse(Potion potion, int durationIn, int amplifierIn) {
        super(potion, durationIn, amplifierIn);
        setCurativeItems(Collections.singletonList(new ItemStack(ModItems.pure_apple)));
    }
}