package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.potions.base.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.potion.PotionEffect;

public class SpellEmpowerPickaxe extends ModSpell {
    private final int SHORT = 1800;
    private final int NORMAL = 3600;
    private final int TEST = 500;

    public SpellEmpowerPickaxe() {
        super(SpellType.EMPOWER_PICKAXE);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        PotionEffect effect = new ModEffect(ModPotions.empower_pickaxe, SHORT);
        context.player.addPotionEffect(effect);
        return true;
    }
}
