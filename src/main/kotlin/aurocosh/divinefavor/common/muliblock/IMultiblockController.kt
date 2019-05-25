package aurocosh.divinefavor.common.muliblock

import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface IMultiblockController {
    fun getControllerWorld(): World
    fun getMultiblockInstance(): MultiBlockInstance?

    fun multiblockDeconstructed()
    fun multiblockDamaged(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState)
    fun tryToFormMultiBlock()
}
