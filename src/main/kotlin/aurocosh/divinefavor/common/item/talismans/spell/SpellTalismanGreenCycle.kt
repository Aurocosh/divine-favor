package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.BlockCrops
import net.minecraft.block.BlockNetherWart
import net.minecraft.block.BlockReed
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SpellTalismanGreenCycle(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = player.getHeldItem(context.hand)

        val spherePoints = UtilCoordinates.getBlocksInSphere(player.position, ConfigSpells.greenCycle.radius)
        val plantPositions = spherePoints.filter { pos -> isValidCrop(pos, context.world) }
        for (pos in plantPositions)
            UtilBlock.removeBlockAndReplant(player, context.world, stack, pos, false, false)
    }

    private fun isValidCrop(pos: BlockPos, world: World): Boolean {
        val state = world.getBlockState(pos)
        val block = state.block
        if (block is BlockReed && world.getBlockState(pos.down()).block !is BlockReed)
            return true
        if (block is BlockCrops && block.isMaxAge(state))
            return true
        return block is BlockNetherWart && state.getValue(BlockNetherWart.AGE) == 3
    }
}
