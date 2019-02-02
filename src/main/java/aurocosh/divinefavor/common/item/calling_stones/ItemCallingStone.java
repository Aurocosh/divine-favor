package aurocosh.divinefavor.common.item.calling_stones;

import aurocosh.divinefavor.common.core.creative_tabs.DivineFavorCreativeTabGems;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemCallingStone extends ModItem {
    public final ModSpirit spirit;
    public final ModMultiBlock multiBlock;

    public ItemCallingStone(String name, ModSpirit spirit, ModMultiBlock multiBlock) {
        super("calling_stone_" + name, "calling_stones/" + name, 0);
        this.spirit = spirit;
        this.multiBlock = multiBlock;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTabGems.INSTANCE);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}