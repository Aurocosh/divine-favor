package aurocosh.divinefavor.common.item.calling_stone;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public class ItemCallingStone extends ItemMod implements IDivineFavorItem {
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;

    public ItemCallingStone(String name, ModSpirit spirit, ModMultiBlock multiBlock) {
        super(name);
        this.spirit = spirit;
        this.multiBlock = multiBlock;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}