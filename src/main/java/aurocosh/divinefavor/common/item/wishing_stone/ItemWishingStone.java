package aurocosh.divinefavor.common.item.wishing_stone;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public class ItemWishingStone extends ItemMod implements IDivineFavorItem {
    private ModFavor favor;
    private int favorCount;

    public ItemWishingStone(String name, ModFavor favor, int favorCount) {
        super(name);
        this.favor = favor;
        this.favorCount = favorCount;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(hand == EnumHand.OFF_HAND)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        if(worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        PlayerDataHandler.PlayerData data = PlayerDataHandler.get(playerIn);
        data.provideSpellCharge(favor.getId(),favorCount);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}