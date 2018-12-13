package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.ModEffectCharge;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellCrushingPalm extends ModSpell {
    private final int USES = 10;

    public SpellCrushingPalm() {
        super(SpellType.CRUSHING_PALM);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new ModEffectCharge(ModPotions.crushing_palm, USES);
        context.player.addPotionEffect(effect);
        return true;
    }
}
