package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import java.util.*

class SpellTalismanCompressItems(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val (player, world) = context.get(playerField, worldField)
        player.openGui(DivineFavor, ConstGuiIDs.COMPRESSED_ITEMS_DROP, world, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
    }
}
