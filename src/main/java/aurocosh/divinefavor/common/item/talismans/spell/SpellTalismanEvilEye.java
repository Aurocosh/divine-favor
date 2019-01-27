package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.util.DamageSource;

import java.util.EnumSet;

public class SpellTalismanEvilEye extends ItemSpellTalisman {
    public SpellTalismanEvilEye(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        return context.target != null;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.target.attackEntityFrom(DamageSource.causePlayerDamage(context.player), 2);
    }
}
