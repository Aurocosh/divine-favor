package aurocosh.divinefavor.common.item.spell_talismans.build.base

import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*

abstract class SpellTalismanBuildShifted(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanBuild(name, spirit, favorCost, options) {
    override val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)
}
