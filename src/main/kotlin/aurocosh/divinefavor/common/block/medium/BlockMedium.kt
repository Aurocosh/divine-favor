package aurocosh.divinefavor.common.block.medium

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.constants.ConstBlockNames
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ChunkCache
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockMedium(name: String, material: Material) : ModBlock(ConstBlockNames.MEDIUM + "_" + name, material, ConstMainTabOrder.MEDIUMS), ITileEntityProvider {

    init {
        setHardness(2.0f)
        setResistance(10.0f)
        soundType = SoundType.METAL
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getActualState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
        val te = if (world is ChunkCache) world.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) else world.getTileEntity(pos)
        return if (te is TileMedium) state.withProperty(STATE, te.getState()).withProperty(STONE, te.getStone()) else super.getActualState(state, world, pos)
    }

    override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        return this.defaultState.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer))
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING, STATE, STONE)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(FACING, EnumFacing.byIndex(meta and 7))
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(FACING).index
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (world.isRemote)
            return true
        val tileEntity = world.getTileEntity(pos) as? TileMedium ?: return false
        if (!tileEntity.isUsableByPlayer(player))
            return false
        else if (!tileEntity.stoneStack.isEmpty)
            player.openGui(DivineFavor, ConstGuiIDs.IRON_MEDIUM_WITH_STONE, world, pos.x, pos.y, pos.z)
        else
            player.openGui(DivineFavor, ConstGuiIDs.IRON_MEDIUM_NO_STONE, world, pos.x, pos.y, pos.z)
        return true
    }

    override fun createNewTileEntity(world: World, meta: Int): TileEntity? {
        return TileMedium()
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        val entity = world.getTileEntity(pos)
        if (entity is TileMedium) {
            val medium = entity as TileMedium?
            medium!!.multiblockDeconstructed()

            UtilEntity.dropItemsOnGround(world, medium.stoneStackHandler, pos)
            UtilEntity.dropItemsOnGround(world, medium.leftStackHandler, pos)
            UtilEntity.dropItemsOnGround(world, medium.rightStackHandler, pos)
        }
        super.breakBlock(world, pos, state)
    }

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.TRANSLUCENT
    }

    companion object {
        val FACING: PropertyDirection = PropertyDirection.create("facing")
        val STATE: PropertyEnum<MediumState> = PropertyEnum.create("state", MediumState::class.java)
        val STONE: PropertyEnum<MediumStone> = PropertyEnum.create("stone", MediumStone::class.java)
    }
}
