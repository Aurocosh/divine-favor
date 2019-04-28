package aurocosh.divinefavor.common.spirit.base

import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class SpiritPunishment {
    open fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {}
}
