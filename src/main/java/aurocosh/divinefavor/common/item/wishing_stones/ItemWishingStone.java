package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
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

    public ItemWishingStone(ModFavor favor, int favorCount, String typeName, int orderIndex) {
        super("wishing_stone_" + typeName + "_" + favor.getName(), "wishing_stones/" + typeName + "/" + favor.getName(), orderIndex);
        this.favor = favor;
        this.favorCount = favorCount;

        setMaxStackSize(64);
        setCreativeTab(DivineFavor.tabGems);
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
        favorData.addFavor(favor.getId(), favorCount);
        new MessageSyncFavor(favor, favorData).sendTo(player);
        stack.shrink(1);
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}