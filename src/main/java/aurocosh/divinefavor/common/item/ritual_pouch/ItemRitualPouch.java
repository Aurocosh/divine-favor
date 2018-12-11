package aurocosh.divinefavor.common.item.ritual_pouch;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGuiIDs;
import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemRitualPouch extends ModItem {
    public ItemRitualPouch() {
        super(ConstItemNames.RITUAL_POUCH,"");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(hand == EnumHand.OFF_HAND)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        playerIn.openGui(DivineFavor.instance, ConstGuiIDs.RITUAL_POUCH, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack item, NBTTagCompound nbt ) {
        if(item.getItem() == ModItems.ritual_pouch)
            return new RitualPouchProvider();
        return null;
    }
}

