package aurocosh.divinefavor.common.effect.base;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModEffect extends PotionEffect {
    public ModEffect(Potion potionIn) {
        super(potionIn);
    }

    public ModEffect(Potion potionIn, int durationIn) {
        super(potionIn, durationIn);
    }

    public ModEffect(Potion potionIn, int durationIn, int amplifierIn) {
        super(potionIn, durationIn, amplifierIn);
    }

    public ModEffect(Potion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
        super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
    }
}
