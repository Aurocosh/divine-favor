package aurocosh.divinefavor.common.effect.base;

import net.minecraft.potion.PotionEffect;

public class ModEffect extends PotionEffect {
    protected final ModPotion modPotion;

    public ModEffect(ModPotion potion) {
        super(potion);
        modPotion = potion;
    }

    public ModEffect(ModPotion potion, int durationIn) {
        super(potion, durationIn);
        modPotion = potion;
    }

    public ModEffect(ModPotion potion, int durationIn, int amplifierIn) {
        super(potion, durationIn, amplifierIn);
        modPotion = potion;
    }
}
