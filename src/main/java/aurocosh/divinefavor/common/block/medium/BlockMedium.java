package aurocosh.divinefavor.common.block.medium;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.util.UtilEntity;
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

public class BlockMedium extends ModBlock implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<MediumState> STATE = PropertyEnum.create("state", MediumState.class);
    public static final PropertyEnum<MediumStone> STONE = PropertyEnum.create("stone", MediumStone.class);

    public BlockMedium(String name, Material material) {
        super(ConstBlockNames.MEDIUM + "_" + name, material, ConstMainTabOrder.MEDIUMS);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileMedium)
            return state.withProperty(STATE, ((TileMedium) te).getState()).withProperty(STONE, ((TileMedium) te).getStone());
        return super.getActualState(state, world, pos);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE, STONE);
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
        if (!(tileEntity instanceof TileMedium))
            return false;
        TileMedium tileMedium = (TileMedium) tileEntity;
        if (!tileMedium.isUsableByPlayer(player))
            return false;

        else if (!tileMedium.getStoneStack().isEmpty())
            player.openGui(DivineFavor.INSTANCE, ConstGuiIDs.IRON_MEDIUM_WITH_STONE, world, pos.getX(), pos.getY(), pos.getZ());
        else
            player.openGui(DivineFavor.INSTANCE, ConstGuiIDs.IRON_MEDIUM_NO_STONE, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileMedium();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileMedium) {
            TileMedium medium = (TileMedium) entity;
            medium.multiblockDeconstructed();

            UtilEntity.dropItemsOnGround(world, medium.getStoneStackHandler(), pos);
            UtilEntity.dropItemsOnGround(world, medium.getLeftStackHandler(), pos);
            UtilEntity.dropItemsOnGround(world, medium.getRightStackHandler(), pos);
        }
        super.breakBlock(world, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
