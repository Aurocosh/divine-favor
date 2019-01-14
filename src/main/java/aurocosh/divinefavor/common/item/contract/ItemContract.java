package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilNbt;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class ItemContract extends ModItem {
    private static final String NBT_PLAYER_ID = "Owner";
    private static final String NBT_PLAYER_NAME = "OwnerName";

    private final ModSpirit spirit;

    public ItemContract(ModSpirit spirit) {
        super("contract_" + spirit.getName(),"contracts/" + spirit.getName());
        this.spirit = spirit;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public ModSpirit getSpirit() {
        return spirit;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

        NBTTagCompound compound = UtilNbt.getEistingOrNewNBT(stack);
        if(compound.hasKey(NBT_PLAYER_ID))
            return null;

        GameProfile profile = player.getGameProfile();
        compound.setString(NBT_PLAYER_ID, profile.getId().toString());
        compound.setString(NBT_PLAYER_NAME, profile.getName());

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Nullable
    public static UUID getPlayerId(ItemStack stack) {
        NBTTagCompound compound = UtilNbt.getEistingOrNewNBT(stack);
        if(!compound.hasKey(NBT_PLAYER_ID))
            return null;
        return UUID.fromString(compound.getString(NBT_PLAYER_ID));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        NBTTagCompound compound = UtilNbt.getEistingOrNewNBT(stack);
        if (compound.hasKey(NBT_PLAYER_NAME))
            tooltip.add(I18n.format("item.divinefavor:contract.player", compound.getString(NBT_PLAYER_NAME)));
    }
}
