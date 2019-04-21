package aurocosh.divinefavor.common.item.bathing_blend.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public abstract class ItemBathingBlend extends ModItem {
    protected final static String TAG_RATE = "rate";
    protected final static String TAG_DURATION = "duration";

    public ItemBathingBlend(String name) {
        super("blend_" + name, "blends/" + name, ConstMainTabOrder.BLENDS);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    public static int getDuration(ItemStack stack) {
        return UtilNbt.getNbt(stack).getInteger(TAG_DURATION);
    }

    public static int getRate(ItemStack stack) {
        return UtilNbt.getNbt(stack).getInteger(TAG_RATE);
    }

    public abstract void applyEffect(EntityLivingBase livingBase);

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public abstract ItemStack makeStack(int count);

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab))
            return;
        items.add(makeStack(1));
    }
}