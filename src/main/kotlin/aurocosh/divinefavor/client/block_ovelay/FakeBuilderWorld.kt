package aurocosh.divinefavor.client.block_ovelay

import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.WorldType
import net.minecraft.world.biome.Biome
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

class FakeBuilderWorld : IBlockAccess {
    private var positions: Set<BlockPos> = HashSet()
    private var state: IBlockState? = null
    private var realWorld: World? = null
    private val AIR = Blocks.AIR.defaultState

    fun setWorldAndState(world: World, iBlockState: IBlockState, coordinates: Set<BlockPos>) {
        this.state = iBlockState
        this.realWorld = world
        positions = coordinates
    }

    @SideOnly(Side.CLIENT)
    override fun getCombinedLight(pos: BlockPos, lightValue: Int): Int {
        return realWorld!!.getCombinedLight(pos, lightValue)
    }

    override fun getTileEntity(pos: BlockPos): TileEntity? {
        return null
    }


    override fun getBlockState(pos: BlockPos): IBlockState {
        return if (positions.contains(pos)) state!! else AIR
    }

    override fun isAirBlock(pos: BlockPos): Boolean {
        return !positions.contains(pos)
    }

    override fun getBiome(pos: BlockPos): Biome {
        return realWorld!!.getBiome(pos)
    }

    override fun getStrongPower(pos: BlockPos, direction: EnumFacing): Int {
        return 0
    }

    override fun getWorldType(): WorldType {
        return realWorld!!.worldType
    }

    override fun isSideSolid(pos: BlockPos, side: EnumFacing, _default: Boolean): Boolean {
        return getBlockState(pos).isSideSolid(this, pos, side)
    }
}
