package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.storage_gem.ItemStorageGem
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanRemoteChest(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = ItemStack(item)
        stack.set(ItemStorageGem.position, context.pos)
        stack.set(ItemStorageGem.dimension, player.dimension)
        player.inventory.addItemStackToInventory(stack)
    }

    override fun validate(context: TalismanContext): Boolean {
        return context.world.getBlock(context.pos) === Blocks.CHEST
    }


}
