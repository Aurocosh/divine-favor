package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulboundLectern extends ModBlock implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<SoulboundLecternState> STATE = PropertyEnum.create("state", SoulboundLecternState.class);
    public static final PropertyEnum<SoulboundLecternGem> GEM = PropertyEnum.create("gem", SoulboundLecternGem.class);

    public BlockSoulboundLectern(String name, Material material) {
        super(ConstBlockNames.SOULBOUND_LECTERN + "_" + name, material, ConstMainTabOrder.LECTERNS);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileSoulboundLectern) {
            TileSoulboundLectern soulboundLectern = (TileSoulboundLectern) te;
            return state.withProperty(STATE, soulboundLectern.getState()).withProperty(GEM, soulboundLectern.getGem());
        }
        return super.getActualState(state, world, pos);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE, GEM);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileSoulboundLectern))
            return false;
        TileSoulboundLectern soulboundLectern = (TileSoulboundLectern) tileEntity;
        if (!soulboundLectern.isUsableByPlayer(player))
            return false;

        if (soulboundLectern.isMultiblockValid())
            player.openGui(DivineFavor.INSTANCE, ConstGuiIDs.SOULBOUND_LECTERN_ACTIVE, world, pos.getX(), pos.getY(), pos.getZ());
        else if (!soulboundLectern.getShardStack().isEmpty())
            player.openGui(DivineFavor.INSTANCE, ConstGuiIDs.SOULBOUND_LECTERN_WITH_SHARD, world, pos.getX(), pos.getY(), pos.getZ());
        else
            player.openGui(DivineFavor.INSTANCE, ConstGuiIDs.SOULBOUND_LECTERN_EMPTY, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSoulboundLectern();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileSoulboundLectern) {
            TileSoulboundLectern lectern = (TileSoulboundLectern) entity;
            UtilEntity.dropItemsOnGround(world, lectern.getShardStackHandler(), pos);
        }

        super.breakBlock(world, pos, state);
    }

    /**
     * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
     * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
     */
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
