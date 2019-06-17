package aurocosh.divinefavor.common.item.spell_talismans.build

import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.build.base.SpellTalismanBuild
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.RelativePositionPropertyWrapper
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*

abstract class SpellTalismanBuildRelative(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuild(name, spirit, favorCost, options) {
    override val positionPropertyWrapper = RelativePositionPropertyWrapper(propertyHandler)
}
