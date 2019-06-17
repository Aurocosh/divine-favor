package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.allInvSequence
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanPlaceTorch(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun validate(context: TalismanContext): Boolean {
        if (!Blocks.TORCH.canPlaceBlockAt(context.world, context.pos))
            return false
        val player = context.player
        if (player.capabilities.isCreativeMode)
            return true
        if (!ConfigSpell.placeTorch.requiresTorches)
            return true
        return player.inventory.allInvSequence().any(torchPredicate)
    }

    override fun performActionServer(context: TalismanContext) {
        val pos = context.pos
        val world = context.world
        val player = context.player

        val isReplaceable = world.isAirOrReplacable(pos)
        val blockPos = if (isReplaceable) pos else pos.add(context.facing.directionVec)

        val block = Blocks.TORCH.getStateForPlacement(world, blockPos, context.facing, 0f, 0f, 0f, 0, player, context.hand)
        UtilBlock.replaceBlock(player, world, blockPos, block)
        consumeTorch(player)
    }

    private fun consumeTorch(player: EntityPlayer) {
        if (!ConfigSpell.placeTorch.requiresTorches)
            return
        if (player.capabilities.isCreativeMode)
            return
        val stack = player.inventory.allInvSequence().firstOrNull(torchPredicate)
        stack?.shrink(1)
    }

    companion object {
        val torchPredicate: (ItemStack) -> Boolean = { val item = it.item; item is ItemBlock && item.block == Blocks.TORCH }
    }
}
