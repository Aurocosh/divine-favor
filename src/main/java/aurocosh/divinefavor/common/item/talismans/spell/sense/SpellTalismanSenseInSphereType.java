package aurocosh.divinefavor.common.item.talismans.spell.sense;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanSenseInSphereType extends SpellTalismanSenseInSphere {
    public SpellTalismanSenseInSphereType(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock, SenseBlockPredicate predicate) {
        super(name, spirit, options, color3f, senseBlock, predicate);
        if (predicate == SenseBlockPredicate.BLOCK)
            DivineFavor.logger.error("Incorrect predicate in class {}. Predicate {}", getClass().getName(), predicate.toString());
    }

    @Override
    protected boolean validate(TalismanContext context) {
        return predicate != SenseBlockPredicate.BLOCK;
    }
}
