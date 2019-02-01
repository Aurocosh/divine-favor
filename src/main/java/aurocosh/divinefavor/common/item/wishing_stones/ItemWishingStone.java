package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTabGems;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavorValue;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemWishingStone extends ModItem {
    private final ModFavor favor;
    private final int favorCount;

    public ItemWishingStone(ModFavor favor, int favorCount, String typeName) {
        super("wishing_stone_" + typeName + "_" + favor.getName(), "wishing_stones/" + typeName + "/" + favor.getName());
        this.favor = favor;
        this.favorCount = favorCount;

        setMaxStackSize(64);
        setCreativeTab(DivineFavorCreativeTabGems.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        EnumActionResult result = addFavor(player, stack) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
        return new ActionResult<>(result, stack);
    }

    private boolean addFavor(EntityPlayer player, ItemStack stack) {
        if (player.world.isRemote)
            return false;
        if (!(stack.getItem() instanceof ItemWishingStone))
            return false;
        FavorData favorData = PlayerData.get(player).getFavorData();
        favorData.get(favor).addValue(favorCount);
        new MessageSyncFavorValue(favor, favorData).sendTo(player);
        stack.shrink(1);
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}