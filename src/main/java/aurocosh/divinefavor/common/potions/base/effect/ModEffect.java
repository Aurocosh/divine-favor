package aurocosh.divinefavor.common.potions.base.effect;

import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collections;

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

    public ModEffect setIsCurse(){
        setCurativeItems(Collections.singletonList(new ItemStack(ModItems.pure_apple)));
        return this;
    }
}
