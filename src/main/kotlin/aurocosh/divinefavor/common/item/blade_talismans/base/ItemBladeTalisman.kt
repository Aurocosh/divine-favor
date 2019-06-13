package aurocosh.divinefavor.common.item.blade_talismans.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit

open class ItemBladeTalisman// Talisman functions
(name: String, texturePath: String, spirit: ModSpirit, favorCost: Int) : ItemTalisman("blade_talisman_$name", "blade_talismans/$texturePath$name", spirit, favorCost) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_BLADE_TALISMANS
    }

    private fun getTexturePath(path: Array<String>): String {
        return path.joinToString("/")
    }

    // Talisman functions
    constructor(name: String, spirit: ModSpirit, favorCost: Int) : this(name, "", spirit, favorCost)

    override fun validateCastType(context: TalismanContext): Boolean {
        return context.castType == CastType.BladeCast
    }
}