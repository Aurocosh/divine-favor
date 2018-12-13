package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.ModEffectCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.potion.PotionEffect;

public class SpellCrushingPalm extends ModSpell {
    private final int USES = 10;

    public SpellCrushingPalm() {
        super("crushing_palm");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new ModEffectCharge(ModPotions.crushing_palm, USES);
        context.player.addPotionEffect(effect);
        return true;
    }
}
