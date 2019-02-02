package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.util.UtilNbt;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemBloodCrystal extends ModItem {
    private static final String NBT_PLAYER_ID = "Owner";
    private static final String NBT_PLAYER_NAME = "OwnerName";

    public ItemBloodCrystal() {
        super("blood_crystal", "blood_crystal");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public static boolean hasOwner(ItemStack stack) {
        return getPlayerId(stack) != null;
    }

    @Nullable
    public static UUID getPlayerId(ItemStack stack) {
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        if (compound.hasKey(NBT_PLAYER_ID))
            return UUID.fromString(compound.getString(NBT_PLAYER_ID));
        return null;
    }

    public static void setOwner(ItemStack stack, EntityPlayer player) {
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        GameProfile profile = player.getGameProfile();
        compound.setString(NBT_PLAYER_ID, profile.getId().toString());
        compound.setString(NBT_PLAYER_NAME, profile.getName());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        NBTTagCompound compound = UtilNbt.getNbt(stack);
        if (compound.hasKey(NBT_PLAYER_NAME))
            tooltip.add(I18n.format("item.divinefavor:blood_crystal.player", compound.getString(NBT_PLAYER_NAME)));
    }
}
