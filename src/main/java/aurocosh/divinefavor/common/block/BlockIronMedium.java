package aurocosh.divinefavor.common.block;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.BlockTileMod;
import aurocosh.divinefavor.common.block.base.IDivineFavorBlock;
import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.constants.LibBlockNames;
import aurocosh.divinefavor.common.constants.LibGuiIDs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockIronMedium extends BlockTileMod implements IDivineFavorBlock {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<MediumState> STATE = PropertyEnum.<MediumState>create("state", MediumState.class);

    public BlockIronMedium() {
        super(LibBlockNames.IRON_MEDIUM, Material.IRON);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileIronMedium) {
            return state.withProperty(STATE, ((TileIronMedium) te).getState());
        }
        return super.getActualState(state, world, pos);
    }


    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getDirectionFromEntityLiving(pos,placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        playerIn.openGui(DivineFavor.instance, LibGuiIDs.IRON_MEDIUM, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileIronMedium();
    }




    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if(!worldIn.isRemote && !canPlaceBlockAt(worldIn, pos)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

}
