package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.item.wishing_stones.ModWishingStones;
import aurocosh.divinefavor.common.item.wishing_stones.WishingStone;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.talismans.ModTalismans;
import aurocosh.divinefavor.common.talismans.Talisman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class ItemWishingStone extends ModItem {

    public ItemWishingStone(String texturePath, String[] variants) {
        super(ConstItemNames.WISHING_STONE, texturePath, variants);
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemWishingStone))
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        WishingStone wishingStone = ModWishingStones.getByMeta(stack.getMetadata());
        if(wishingStone == null)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        wishingStone.use(playerIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}