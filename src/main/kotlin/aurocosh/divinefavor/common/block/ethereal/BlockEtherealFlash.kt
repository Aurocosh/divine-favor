package aurocosh.divinefavor.common.block.ethereal

import aurocosh.divinefavor.DivineFavor
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class BlockEtherealFlash(name: String, lightLevel: Int, private val despawnDelay: Int) : BlockEtherealLight(name, Material.ROCK, lightLevel) {

    init {
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
        worldIn.scheduleUpdate(pos, this, despawnDelay)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        worldIn.setBlockToAir(pos)
    }
}