package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilPlayer;
import aurocosh.divinefavor.common.util.UtilTeleport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWarpPebble extends ModItem {
    public static String TAG_POSITION = "Position";
    public static String TAG_DIMENSION = "Dimension";

    public ItemWarpPebble() {
        super("warp_pebble","warp_pebble");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!teleportPlayer(stack, player, world))
            return new ActionResult<>(EnumActionResult.PASS, stack);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if(!teleportPlayer(stack, player, world))
            return EnumActionResult.PASS;
        return EnumActionResult.SUCCESS;
    }

    private boolean teleportPlayer(ItemStack stack, EntityPlayer player, World world){
        if(world.isRemote)
            return false;
        if(stack.isEmpty())
            return false;
        if(!(stack.getItem() instanceof ItemWarpPebble))
            return false;
        NBTTagCompound nbt = UtilNbt.getEistingOrNewNBT(stack);
        if(!UtilNbt.checkForTags(nbt, TAG_POSITION, TAG_DIMENSION))
            return false;

        BlockPos destination = UtilNbt.getBlockPos(nbt, TAG_POSITION);
        int dimension = nbt.getInteger(TAG_DIMENSION);
        if(dimension != player.dimension)
            return false;

        stack.shrink(1);
        if (stack.isEmpty())
            player.inventory.deleteStack(stack);
        UtilEntity.teleport(player, dimension, destination);
        return true;
    }
}