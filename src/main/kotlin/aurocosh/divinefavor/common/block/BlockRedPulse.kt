package aurocosh.divinefavor.common.block

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlockAir
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockRedPulse(name: String, private val redLevel: Int, lightLevel: Int, private val despawnDelay: Int) : ModBlockAir(name, Material.ROCK, ConstMainTabOrder.OTHER_BLOCKS) {

    init {
        lightValue = lightLevel
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getRenderType(state: IBlockState?): EnumBlockRenderType {
        return EnumBlockRenderType.MODEL
    }

    override fun canProvidePower(state: IBlockState): Boolean {
        return true
    }

    override fun getWeakPower(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Int {
        return redLevel
    }

    override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
        worldIn.scheduleUpdate(pos, this, despawnDelay)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        worldIn.setBlockToAir(pos)
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.TRANSLUCENT
    }
}