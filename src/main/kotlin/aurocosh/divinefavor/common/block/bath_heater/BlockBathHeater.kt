package aurocosh.divinefavor.common.block.bath_heater

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.constants.ConstBlockNames
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ChunkCache
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk

class BlockBathHeater : ModBlock(ConstBlockNames.BATH_HEATER, Material.ROCK, ConstMainTabOrder.OTHER_BLOCKS), ITileEntityProvider {
    init {
        setHardness(2.0f)
        setResistance(10.0f)
        soundType = SoundType.STONE
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getActualState(state: IBlockState, world: IBlockAccess?, pos: BlockPos?): IBlockState {
        val te = if (world is ChunkCache) world.getTileEntity(pos!!, Chunk.EnumCreateEntityType.CHECK) else world?.getTileEntity(pos!!)
        return if (te is TileBathHeater) state.withProperty(STATE, te.state) else super.getActualState(state, world!!, pos!!)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, STATE)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return 0
    }

    override fun onBlockActivated(world: World, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val tileEntity = world.getTileEntity(pos!!) as? TileBathHeater ?: return false
        if (!tileEntity.isUsableByPlayer(player!!))
            return false
        player.openGui(DivineFavor, ConstGuiIDs.BATH_HEATER, world, pos.x, pos.y, pos.z)
        return true
    }

    override fun createNewTileEntity(world: World?, meta: Int): TileEntity? {
        return TileBathHeater()
    }

    companion object {
        val STATE: PropertyEnum<BathHeaterState> = PropertyEnum.create("state", BathHeaterState::class.java)
    }
}
