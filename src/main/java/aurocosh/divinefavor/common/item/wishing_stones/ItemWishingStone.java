package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
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
    private final ModFavor favor;
    private final int favorCount;

    public ItemWishingStone(String name, ModFavor favor, int favorCount) {
        super("wishing_stone_" + name, "wishing_stones/");
        this.favor = favor;
        this.favorCount = favorCount;

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

        int count = playerIn.isSneaking() ? 8 : favorCount;
        gainFavor(playerIn, count);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public void gainFavor(EntityPlayer playerIn, int count) {
        IFavorHandler favorHandler = playerIn.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        int favorValue = favorHandler.addFavor(favor.id, count);
        if (playerIn instanceof EntityPlayerMP) {
            MessageSyncFavor message = new MessageSyncFavor(favor.id, favorValue);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) playerIn);
        }
    }
}