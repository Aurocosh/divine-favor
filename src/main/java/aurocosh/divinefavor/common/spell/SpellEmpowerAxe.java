package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.effect.ModPotionEffects;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.potion.PotionEffect;

public class SpellEmpowerAxe extends Spell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;

    public SpellEmpowerAxe() {
        super(LibSpellNames.EMPOWER_AXE);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new PotionEffect(ModPotionEffects.empower_axe, NORMAL);
        context.playerIn.addPotionEffect(effect);
        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}
