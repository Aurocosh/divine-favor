package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.ItemWarpMarker
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanRemoteChest(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = ItemStack(item)
        val nbt = UtilNbt.getNbt(stack)
        UtilNbt.setBlockPos(nbt, ItemWarpMarker.TAG_POSITION, context.pos)
        nbt.setInteger(ItemWarpMarker.TAG_DIMENSION, player.dimension)
        player.inventory.addItemStackToInventory(stack)
    }

    override fun validate(context: TalismanContext): Boolean {
        return context.world.getBlock(context.pos) === Blocks.CHEST;
    }
}
