package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanWarpMarker(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = ItemStack(item)
        stack.set(ItemWarpMarker.position, player.position)
        stack.set(ItemWarpMarker.dimension, player.dimension)
        player.inventory.addItemStackToInventory(stack)
    }
}
