package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
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
