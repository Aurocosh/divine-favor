package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.ItemWarpMarker
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.setBlockPos
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanWarpMarker(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = ItemStack(item)
        val compound = stack.compound
        compound.setBlockPos(ItemWarpMarker.TAG_POSITION, player.position)
        compound.setInteger(ItemWarpMarker.TAG_DIMENSION, player.dimension)
        player.inventory.addItemStackToInventory(stack)
    }
}
