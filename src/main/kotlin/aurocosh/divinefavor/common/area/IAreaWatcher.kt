package aurocosh.divinefavor.common.area

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface IAreaWatcher {
    fun getAreaWorld(): World
    fun getArea(): WorldArea

    fun blockChanged(world: World, pos: BlockPos, state: IBlockState)
}
