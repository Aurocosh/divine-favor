package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.effect.common.ModPotionEffects;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellEmpowerAxe extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;
    private final int TEST = 100;

    public SpellEmpowerAxe() {
        super(SpellType.EMPOWER_AXE, true);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new PotionEffect(ModPotionEffects.empower_axe, TEST);
        context.player.addPotionEffect(effect);
        return true;
    }
}
