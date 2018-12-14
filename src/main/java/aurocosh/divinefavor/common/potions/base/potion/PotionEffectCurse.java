package aurocosh.divinefavor.common.potions.base.potion;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

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
        setCurativeItems(new ArrayList<>());
    }
}