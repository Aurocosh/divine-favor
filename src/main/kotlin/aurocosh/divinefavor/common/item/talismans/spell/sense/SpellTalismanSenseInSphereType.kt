package aurocosh.divinefavor.common.item.talismans.spell.sense

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.entries.talismans.spell.generic.SenseConfig
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*
import javax.vecmath.Color3f

class SpellTalismanSenseInSphereType(name: String, spirit: ModSpirit, options: EnumSet<SpellOptions>, color3f: Color3f, senseConfig: SenseConfig, predicate: SenseBlockPredicate) : SpellTalismanSenseInSphere(name, spirit, options, color3f, senseConfig, predicate) {
    init {
        if (predicate == SenseBlockPredicate.BLOCK)
            DivineFavor.logger.error("Incorrect predicate in class {}. Predicate {}", javaClass.name, predicate.toString())
    }

    override fun validate(context: TalismanContext): Boolean {
        return predicate != SenseBlockPredicate.BLOCK
    }
}
