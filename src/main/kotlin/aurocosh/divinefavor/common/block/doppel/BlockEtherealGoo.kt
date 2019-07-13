package aurocosh.divinefavor.common.block.doppel

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.FakeRenderWorld
import aurocosh.divinefavor.common.block.base.ModBlock
import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.models.BlockStateProperty
import aurocosh.divinefavor.common.models.EtherealGooBakedModel
import aurocosh.divinefavor.common.state_mappers.common.ICustomColorHandlerBlock
import aurocosh.divinefavor.common.state_mappers.common.ICustomStateMappedBlock
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.block.statemap.IStateMapper
import net.minecraft.client.renderer.block.statemap.StateMapperBase
import net.minecraft.client.renderer.color.BlockColors
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.property.ExtendedBlockState
import net.minecraftforge.common.property.IExtendedBlockState
import net.minecraftforge.common.property.IUnlistedProperty
import net.minecraftforge.fml.common.Optional
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import team.chisel.ctm.api.IFacade

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

@Optional.Interface(iface = "team.chisel.ctm.api.IFacade", modid = "ctm-api")
class BlockEtherealGoo : ModBlock("ethereal_goo", Material.ROCK, 0), IFacade, ICustomStateMappedBlock, ICustomColorHandlerBlock {

    init {
        defaultState = blockState.baseState.withProperty(BRIGHT, true).withProperty(NEIGHBOR_BRIGHTNESS, false)
        creativeTab = DivineFavor.TAB_MAIN
    }

    @SideOnly(Side.CLIENT)
    override fun getCustomStateMapper(): IStateMapper {
        return object : StateMapperBase() {
            override fun getModelResourceLocation(iBlockState: IBlockState): ModelResourceLocation {
                return EtherealGooBakedModel.modelFacade
            }
        }
    }

//    override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item {
//        return ModItems.bone_key
//    }

    override fun canSilkHarvest(world: World?, pos: BlockPos?, state: IBlockState, player: EntityPlayer?): Boolean {
        return false
    }

    override fun hasTileEntity(state: IBlockState?): Boolean {
        return true
    }

    override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
        return TileEtherealGoo()
    }

    override fun getExtendedState(state: IBlockState, world: IBlockAccess, pos: BlockPos): IBlockState {
        val extendedBlockState = super.getExtendedState(state, world, pos) as IExtendedBlockState
        val mimicBlock = getActualMimicBlock(world, pos)
        if (mimicBlock != null) {
            val fakeRenderWorld = FakeRenderWorld()
            fakeRenderWorld.setState(world, mimicBlock, pos)
            val extState = mimicBlock.block.getExtendedState(mimicBlock, fakeRenderWorld, pos)
            return extendedBlockState.withProperty(FACADE_ID, mimicBlock).withProperty(FACADE_EXT_STATE, extState)
        }
        return extendedBlockState
    }

    private fun getActualMimicBlock(blockAccess: IBlockAccess?, pos: BlockPos?): IBlockState? {
        return try {
            if (blockAccess == null || pos == null)
                return null
            val tile = blockAccess.getTileEntity(pos) as? TileEtherealGoo
            tile?.actualBlockState
        } catch (var8: Exception) {
            null
        }
    }

    override fun createBlockState(): BlockStateContainer {
        val listedProperties = arrayOf<IProperty<*>>(BRIGHT, NEIGHBOR_BRIGHTNESS)
        val unlistedProperties = arrayOf<IUnlistedProperty<*>>(FACADE_ID, FACADE_EXT_STATE)
        return ExtendedBlockState(this, listedProperties, unlistedProperties)
    }

    override fun getRenderType(state: IBlockState?): EnumBlockRenderType {
        return EnumBlockRenderType.MODEL
    }

    @SideOnly(Side.CLIENT)
    override fun canRenderInLayer(state: IBlockState?, layer: BlockRenderLayer?): Boolean {
        return true
        // return true // delegated to FacadeBakedModel#getQuads
    }

    override fun isOpaqueCube(p_isFullBlock_1_: IBlockState?): Boolean {
        return false
    }

    override fun isFullCube(state: IBlockState?): Boolean {
        return false
    }

    override fun getLightOpacity(state: IBlockState, world: IBlockAccess?, pos: BlockPos?): Int {
        val bright = state.getValue(BRIGHT)
        return if (bright) 0 else 255
    }

    override fun doesSideBlockRendering(state: IBlockState, world: IBlockAccess, pos: BlockPos, face: EnumFacing): Boolean {
        val mimicBlock = getActualMimicBlock(world, pos)
        return mimicBlock?.block?.doesSideBlockRendering(mimicBlock, world, pos, face) ?: false
    }

    @SideOnly(Side.CLIENT)
    override fun initColorHandler(blockColors: BlockColors) {
        val colorHandler = IBlockColor { _, world, pos, tintIndex ->
            val mimicBlock = getActualMimicBlock(world, pos)
            if (mimicBlock != null) blockColors.colorMultiplier(mimicBlock, world, pos, tintIndex) else -1
        }
        blockColors.registerBlockColorHandler(colorHandler, this)
    }

    override fun getBlockFaceShape(world: IBlockAccess, state: IBlockState, pos: BlockPos, face: EnumFacing): BlockFaceShape {
        val mimicBlock = getActualMimicBlock(world, pos)
        return try {
            if (mimicBlock == null) BlockFaceShape.SOLID else mimicBlock.block.getBlockFaceShape(world, mimicBlock, pos, face)
        } catch (var8: Exception) {
            BlockFaceShape.SOLID
        }
    }

    override fun addCollisionBoxToList(state: IBlockState, world: World, pos: BlockPos, entityBox: AxisAlignedBB, collidingBoxes: List<AxisAlignedBB>, entityIn: Entity?, isActualState: Boolean) {
        val mimicBlock = getActualMimicBlock(world, pos)
        if (mimicBlock == null) {
            super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entityIn, isActualState)
        } else {
            try {
                mimicBlock.block.addCollisionBoxToList(mimicBlock, world, pos, entityBox, collidingBoxes, entityIn, isActualState)
            } catch (var8: Exception) {
                super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entityIn, isActualState)
            }
        }
    }

    override fun getCollisionBoundingBox(blockState: IBlockState, world: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        val mimicBlock = getActualMimicBlock(world, pos) ?: return super.getBoundingBox(blockState, world, pos)
        return try {
            mimicBlock.block.getBoundingBox(mimicBlock, world, pos)
        } catch (var8: Exception) {
            super.getBoundingBox(blockState, world, pos)
        }

    }

    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
        val fakeWorld = FakeRenderWorld()
        val mimicBlock = getActualMimicBlock(blockAccess, pos) ?: return shouldOriginalSideBeRendered(blockState, blockAccess, pos, side)

        val sideBlockState: IBlockState = getSideBlockState(blockAccess, pos, side)

        fakeWorld.setState(blockAccess, mimicBlock, pos)
        fakeWorld.setState(blockAccess, sideBlockState, pos.offset(side))

        return try {
            mimicBlock.block.shouldSideBeRendered(mimicBlock, fakeWorld, pos, side)
        } catch (var8: Exception) {
            return shouldOriginalSideBeRendered(blockState, blockAccess, pos, side)
        }
    }

    private fun getSideBlockState(blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): IBlockState {
        val sideBlockState: IBlockState = blockAccess.getBlockState(pos.offset(side))
        if (sideBlockState.block !== ModBlocks.etherealGooBlock)
            return sideBlockState
        return getActualMimicBlock(blockAccess, pos.offset(side)) ?: sideBlockState
    }

    private fun shouldOriginalSideBeRendered(blockState: IBlockState, blockAccess: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
        val sideBlockState: IBlockState = getSideBlockState(blockAccess, pos, side)
        return if (sideBlockState.block === this) {
            false
        } else {
            super.shouldSideBeRendered(blockState, blockAccess, pos, side)
        }
    }

    override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        val mimicBlock = getActualMimicBlock(source, pos) ?: return super.getBoundingBox(state, source, pos)
        return try {
            mimicBlock.block.getBoundingBox(mimicBlock, source, pos)
        } catch (var8: Exception) {
            super.getBoundingBox(state, source, pos)
        }

    }

    @SideOnly(Side.CLIENT)
    override fun getSelectedBoundingBox(state: IBlockState, world: World, pos: BlockPos): AxisAlignedBB {
        val mimicBlock = getActualMimicBlock(world, pos) ?: return super.getSelectedBoundingBox(state, world, pos)
        return try {
            mimicBlock.block.getSelectedBoundingBox(mimicBlock, world, pos)
        } catch (var8: Exception) {
            super.getSelectedBoundingBox(state, world, pos)
        }

    }

    override fun isNormalCube(state: IBlockState, world: IBlockAccess?, pos: BlockPos?): Boolean {
        val mimicBlock = getActualMimicBlock(world, pos) ?: return super.isNormalCube(state, world, pos)
        return try {
            mimicBlock.block.isNormalCube(mimicBlock, world, pos)
        } catch (var8: Exception) {
            super.isNormalCube(state, world, pos)
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getAmbientOcclusionLightValue(state: IBlockState): Float {
        val bright = state.getValue(BRIGHT)
        val neighborBrightness = state.getValue(NEIGHBOR_BRIGHTNESS)
        return if (bright || neighborBrightness) 1f else 0.2f
    }

    override fun getUseNeighborBrightness(state: IBlockState): Boolean {
        return state.getValue(NEIGHBOR_BRIGHTNESS)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState
                .withProperty(BRIGHT, meta % 2 == 1)
                .withProperty(NEIGHBOR_BRIGHTNESS, meta / 2 == 1)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        val value = if (state.getValue(BRIGHT)) 1 else 0
        return if (state.getValue(NEIGHBOR_BRIGHTNESS)) value + 2 else value
    }

    /**
     * The below implements support for CTM's Connected Textures to work properly
     *
     * @param world IBlockAccess
     * @param pos BlockPos
     * @param side EnumFacing
     * @return IBlockState
     *
     */
    @Deprecated("see {@link IFacade#getFacade(IBlockAccess, BlockPos, EnumFacing, BlockPos)}")
    override fun getFacade(world: IBlockAccess, pos: BlockPos, side: EnumFacing?): IBlockState {
        val mimicBlock = getActualMimicBlock(world, pos)
        return mimicBlock ?: world.getBlockState(pos)
        //return mimicBlock;
    }


    companion object {

        //public static final ConstructionProperty FACADEID = new ConstructionProperty("facadeid");
        val BRIGHT = PropertyBool.create("bright")
        val NEIGHBOR_BRIGHTNESS = PropertyBool.create("neighbor_brightness")

        val FACADE_ID: IUnlistedProperty<IBlockState> = BlockStateProperty("facadestate")
        val FACADE_EXT_STATE: IUnlistedProperty<IBlockState> = BlockStateProperty("facadeextstate")
    }
}