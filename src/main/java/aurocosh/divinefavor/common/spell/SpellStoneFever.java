package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellStoneFever extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;
    private final int TEST = 500;

    public SpellStoneFever() {
        super("stone_fever");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new ModEffect(ModPotions.stone_fever, NORMAL);
        context.player.addPotionEffect(effect);
        return true;
    }
}
