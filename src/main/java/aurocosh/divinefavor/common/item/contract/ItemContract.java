package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemContract extends ModItem {
    public ItemContract(String name, String texturePath) {
        super("contract_" + name, "contracts/" + texturePath, ConstMainTabOrder.CONTRACTS);
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
