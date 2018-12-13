package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCallingStone extends ModItem {
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;

    public ItemCallingStone(String name, ModSpirit spirit, ModMultiBlock multiBlock) {
        super(ConstItemNames.CALLING_STONE + "_" + name, "calling_stones/");
        this.spirit = spirit;
        this.multiBlock = multiBlock;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}