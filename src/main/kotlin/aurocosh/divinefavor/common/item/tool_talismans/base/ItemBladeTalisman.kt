package aurocosh.divinefavor.common.item.tool_talismans.base

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit

open class ItemToolTalisman
(name: String, texturePath: String, spirit: ModSpirit, favorCost: Int) : ItemTalisman("tool_talisman_$name", "tool_talismans/$texturePath$name", spirit, favorCost) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_TOOL_TALISMANS
    }

    private fun getTexturePath(path: Array<String>): String {
        return path.joinToString("/")
    }

    // Talisman functions
    constructor(name: String, spirit: ModSpirit, favorCost: Int) : this(name, "", spirit, favorCost)

    override fun preValidate(context: TalismanContext): Boolean {
        return context.castType == CastType.PickCast && super.preValidate(context)
    }
}