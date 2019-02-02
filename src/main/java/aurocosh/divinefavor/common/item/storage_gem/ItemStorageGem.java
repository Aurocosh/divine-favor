package aurocosh.divinefavor.common.item.storage_gem;

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.util.UtilFacing;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemStorageGem extends ModItem {
    public static String TAG_POSITION = "Position";
    public static String TAG_DIMENSION = "Dimension";

    public ItemStorageGem() {
        super("storage_gem", "storage_gem");
        setMaxStackSize(16);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!openChest(stack, world, player))
            return new ActionResult<>(EnumActionResult.PASS, stack);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!openChest(stack, world, player))
            return EnumActionResult.PASS;
        return EnumActionResult.SUCCESS;
    }

    public boolean openChest(ItemStack stack, World world, EntityPlayer playerIn) {
        if (world.isRemote) {
            return true;
        }
        NBTTagCompound tag = UtilNbt.getNbt(stack);
        if (!UtilNbt.checkForTags(tag, TAG_POSITION, TAG_DIMENSION))
            return false;
        BlockPos pos = UtilNbt.getBlockPos(tag, TAG_POSITION);
        int dimension = tag.getInteger(TAG_DIMENSION);
        if (playerIn.dimension != dimension)
            return false;

        ILockableContainer ilockablecontainer = getContainer(world, pos);
        if (ilockablecontainer == null)
            return false;

        playerIn.displayGUIChest(new StorageGemInventoryWrapper(ilockablecontainer));
        playerIn.addStat(StatList.CHEST_OPENED);
        stack.shrink(1);
        return true;
    }

    @Nullable
    public ILockableContainer getContainer(World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityChest))
            return null;
        TileEntityChest tileChest = (TileEntityChest) tileEntity;
        if (tileChest.getChestType() != BlockChest.Type.BASIC)
            return null;
        EnumFacing connectedFacing = findConnectedChest(worldIn, pos);
        if (connectedFacing == null)
            return tileChest;

        BlockPos connectedPos = pos.offset(connectedFacing);
        TileEntity tileMergedChest = worldIn.getTileEntity(connectedPos);

        if (connectedFacing != EnumFacing.WEST && connectedFacing != EnumFacing.NORTH)
            return new InventoryLargeChest("container.chestDouble", tileChest, (TileEntityChest) tileMergedChest);
        else
            return new InventoryLargeChest("container.chestDouble", (TileEntityChest) tileMergedChest, tileChest);
    }

    private EnumFacing findConnectedChest(World worldIn, BlockPos pos) {
        for (EnumFacing facing : UtilFacing.horizontal) {
            BlockPos blockPos = pos.offset(facing);
            Block block = worldIn.getBlockState(blockPos).getBlock();
            if (block == Blocks.CHEST)
                return facing;
        }
        return null;
    }
}