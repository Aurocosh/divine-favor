package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstGemTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCallingStone extends ModItem {
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;

    public ItemCallingStone(String name, ModSpirit spirit, ModMultiBlock multiBlock) {
        super("calling_stone_" + name, "calling_stones/" + name, ConstGemTabOrder.CALLING_STONE);
        this.spirit = spirit;
        this.multiBlock = multiBlock;

        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_GEMS);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}