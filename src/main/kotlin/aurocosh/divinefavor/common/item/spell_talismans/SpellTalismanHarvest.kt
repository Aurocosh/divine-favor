package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import com.google.common.collect.ImmutableSet
import net.minecraft.block.BlockCrops
import net.minecraft.block.BlockNetherWart
import net.minecraft.block.BlockReed
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class SpellTalismanHarvest(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val player = context.player
        val stack = player.getHeldItem(context.hand)

        val spherePoints = UtilCoordinates.getBlocksInSphere(player.position, ConfigSpell.harvest.radius)
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
        val ALLOWED_MATERIALS: ImmutableSet<Material> = ImmutableSet.of(
                Material.WEB,
                Material.LEAVES,
                Material.VINE,
                Material.GOURD,
                Material.CACTUS)
    }
}
