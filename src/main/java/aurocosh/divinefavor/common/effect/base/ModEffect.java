package aurocosh.divinefavor.common.effect.base;

import net.minecraft.potion.PotionEffect;

public class ModEffect extends PotionEffect {
    private final ModPotion modPotion;
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

    public ModEffect(ModPotion potion, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
        super(potion, durationIn, amplifierIn, ambientIn, showParticlesIn);
        modPotion = potion;
    }
}
