package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.constants.ConstBlockNames;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.ItemBloodCrystal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.UUID;

public class BlockSoulboundLectern extends ModBlock implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<SoulboundLecternState> STATE = PropertyEnum.create("state", SoulboundLecternState.class);

    public BlockSoulboundLectern() {
        super(ConstBlockNames.SOULBOUND_LECTERN, Material.IRON);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileSoulboundLectern)
            return state.withProperty(STATE, ((TileSoulboundLectern) te).getState());
        return super.getActualState(state, world, pos);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
            return false;
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileSoulboundLectern))
            return false;
        TileSoulboundLectern soulboundLectern = (TileSoulboundLectern) tileEntity;
        if (!soulboundLectern.isUsableByPlayer(player))
            return false;

        IItemHandler crystalHandler = soulboundLectern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        ItemStack stack = crystalHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            UUID playerUUID = player.getGameProfile().getId();
            UUID stackUUID = ItemBloodCrystal.getPlayerId(stack);
            if (!playerUUID.equals(stackUUID))
                return false;
            player.openGui(DivineFavor.instance, ConstGuiIDs.SOULBOUND_LECTERN_BOUND, world, pos.getX(), pos.getY(), pos.getZ());
            return true;

        }
        player.openGui(DivineFavor.instance, ConstGuiIDs.SOULBOUND_LECTERN_UNBOUND, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSoulboundLectern();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        TileEntity entity = worldIn.getTileEntity(pos);
        if (!(entity instanceof TileSoulboundLectern))
            return;
        TileSoulboundLectern medium = (TileSoulboundLectern) entity;
        // TODO
    }
}
