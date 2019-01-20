package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilNbt;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemInviteMarker extends ModItem {
    public static String TAG_PLAYER_UUID = "PlayerUUID";

    private boolean canTeleportToDimensions;

    public ItemInviteMarker(String name, boolean canTeleportToDimensions) {
        super(name, name);
        this.canTeleportToDimensions = canTeleportToDimensions;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!teleportPlayer(stack, player, world))
            return new ActionResult<>(EnumActionResult.PASS, stack);
        return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
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
        if(!(stack.getItem() instanceof ItemInviteMarker))
            return false;
        if(!UtilNbt.checkForTag(stack, TAG_PLAYER_UUID))
            return false;

        NBTTagCompound nbt = UtilNbt.getTag(stack);
        UUID uuid = UUID.fromString(nbt.getString(TAG_PLAYER_UUID));
        PlayerList playerList = world.getMinecraftServer().getPlayerList();
        EntityPlayerMP targetPlayer = playerList.getPlayerByUUID(uuid);
        if (player == null)
            return false;

        if(!canTeleportToDimensions && targetPlayer.dimension != player.dimension)
            return false;
        UtilEntity.teleport(player, targetPlayer.dimension, targetPlayer.getPosition());
        UtilPlayer.damageStack(player, stack);
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        NBTTagCompound compound = UtilNbt.getTag(stack);
        if (compound.hasKey(TAG_PLAYER_UUID))
            tooltip.add(I18n.format("item.divinefavor:contract.player", compound.getString(TAG_PLAYER_UUID)));
    }
}