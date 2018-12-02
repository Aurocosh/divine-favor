package aurocosh.divinefavor.common.item.symbol;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.IDivineFavorItem;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.arl.item.ItemMod;

public class ItemCallingStone extends ItemMod implements IDivineFavorItem {
    private ModSpirit spirit;

    public ItemCallingStone(String name, ModSpirit spirit) {
        super(name);
        this.spirit = spirit;
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    public ModSpirit getSpirit() {
        return spirit;
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