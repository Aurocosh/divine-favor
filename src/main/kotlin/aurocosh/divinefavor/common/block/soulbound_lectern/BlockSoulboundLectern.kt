package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.constants.ConstBlockNames
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
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

class BlockSoulboundLectern(name: String, material: Material) : ModBlock(ConstBlockNames.SOULBOUND_LECTERN + "_" + name, material, ConstMainTabOrder.LECTERNS), ITileEntityProvider {

    init {
        setHardness(2.0f)
        setResistance(10.0f)
        soundType = SoundType.METAL
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getActualState(state: IBlockState, world: IBlockAccess?, pos: BlockPos): IBlockState {
        val te = if (world is ChunkCache) world.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) else world?.getTileEntity(pos)
        if (te is TileSoulboundLectern) {
            val soulboundLectern = te as TileSoulboundLectern?
            return state.withProperty(STATE, soulboundLectern!!.state).withProperty(GEM, soulboundLectern.gem)
        }
        return super.getActualState(state, world, pos)
    }

    override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        return this.defaultState.withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING, STATE, GEM)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(FACING, EnumFacing.byIndex(meta and 7))
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(FACING).index
    }

    override fun onBlockActivated(world: World, pos: BlockPos?, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (world.isRemote)
            return true
        val tileEntity = world.getTileEntity(pos!!) as? TileSoulboundLectern ?: return false
        if (!tileEntity.isUsableByPlayer(player))
            return false

        if (tileEntity.isMultiblockValid)
            player.openGui(DivineFavor, ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE, world, pos.x, pos.y, pos.z)
        else if (!tileEntity.shardStack.isEmpty)
            player.openGui(DivineFavor, ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD, world, pos.x, pos.y, pos.z)
        else
            player.openGui(DivineFavor, ConstGuiIDs.SOULBOUND_LECTERN_EMPTY, world, pos.x, pos.y, pos.z)
        return true
    }

    override fun createNewTileEntity(world: World?, meta: Int): TileEntity? {
        return TileSoulboundLectern()
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        val entity = world.getTileEntity(pos)
        if (entity is TileSoulboundLectern) {
            val lectern = entity as TileSoulboundLectern?
            UtilEntity.dropItemsOnGround(world, lectern!!.shardStackHandler, pos)
        }

        super.breakBlock(world, pos, state)
    }

    /**
     * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
     * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
     */
    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer {
        return BlockRenderLayer.TRANSLUCENT
    }

    companion object {
        val FACING = BlockHorizontal.FACING
        val STATE = PropertyEnum.create("state", SoulboundLecternState::class.java)
        val GEM = PropertyEnum.create("gem", SoulboundLecternGem::class.java)
    }
}
