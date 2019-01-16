package aurocosh.divinefavor.common.potions.base.effect;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModEffect extends PotionEffect {
    protected final Potion modPotion;

    public ModEffect(Potion potion) {
        super(potion);
        modPotion = potion;
    }

    public ModEffect(Potion potion, int durationIn) {
        super(potion, durationIn);
        modPotion = potion;
    }

    public ModEffect(Potion potion, int durationIn, int amplifierIn) {
        super(potion, durationIn, amplifierIn);
        modPotion = potion;
    }
}
