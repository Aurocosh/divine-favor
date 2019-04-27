package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import com.google.common.collect.ImmutableSet
import net.minecraft.block.BlockCrops
import net.minecraft.block.BlockNetherWart
import net.minecraft.block.BlockReed
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SpellTalismanHarvest(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val stack = player.getHeldItem(context.hand)

        val spherePoints = UtilCoordinates.getBlocksInSphere(player.position, ConfigSpells.harvest.radius)
        val plantPositions = spherePoints.filter { pos -> isValidCrop(pos, context.world) }
        for (pos in plantPositions)
            UtilBlock.removeBlock(player, context.world, stack, pos, true, false, false)
    }

    private fun isValidCrop(pos: BlockPos, world: World): Boolean {
        val state = world.getBlockState(pos)
        val block = state.block
        if (block is BlockReed && world.getBlockState(pos.down()).block !is BlockReed)
            return true
        if (block is BlockCrops && block.isMaxAge(state))
            return true
        return if (block is BlockNetherWart && state.getValue(BlockNetherWart.AGE) == 3) true else ALLOWED_MATERIALS.contains(state.material)
    }

    companion object {
        val ALLOWED_MATERIALS = ImmutableSet.of(net.minecraft.block.material.Material.WEB,
                net.minecraft.block.material.Material.LEAVES,
                net.minecraft.block.material.Material.VINE,
                net.minecraft.block.material.Material.GOURD,
                net.minecraft.block.material.Material.CACTUS)!!
    }
}
