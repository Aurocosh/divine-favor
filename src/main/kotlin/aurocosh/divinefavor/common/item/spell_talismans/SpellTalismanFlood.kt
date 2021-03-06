package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.getMaterial
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.BlockLiquid
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.Vec3d
import java.util.*

class SpellTalismanFlood(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
//    val testAction = actionHandler.registerAction("test", serverAction = this::perfServer)


    override fun performActionServer(context: CastContext) {
        val world = context.world
        val posList = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpell.flood.radius)
        val flowingWaterBlocks = posList
                .filter(world::getMaterial, Material.WATER::equals)
                .filter(world::getBlockState) { state -> state.getValue(BlockLiquid.LEVEL) != 0 }

        val state = Blocks.WATER.defaultState
        for (pos in flowingWaterBlocks)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }

    private fun perfClient(player: EntityPlayer, stack: ItemStack) {


    }

    private fun perfServer(player: EntityPlayer, stack: ItemStack) {
        val entities = player.world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB(player.position).grow(20.0))
        val vec3d = Vec3d(0.0, 1.0, 0.0)
        entities.forEach { UtilEntity.addVelocity(it, vec3d, 4f) }
    }
}
