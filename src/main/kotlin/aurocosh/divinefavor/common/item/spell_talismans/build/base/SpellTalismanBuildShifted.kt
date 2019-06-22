package aurocosh.divinefavor.common.item.spell_talismans.build.base

import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.spirit.base.ModSpirit

abstract class SpellTalismanBuildShifted(name: String, spirit: ModSpirit) : SpellTalismanBuild(name, spirit) {
    override val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)
}
