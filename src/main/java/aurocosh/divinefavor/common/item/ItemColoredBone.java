package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.common.constants.items.ConstItemNames;
import aurocosh.divinefavor.common.core.DivineFavorCreativeTab;
import aurocosh.divinefavor.common.item.base.ModItem;

public class ItemColoredBone extends ModItem {
    public ItemColoredBone() {
        super(ConstItemNames.COLORED_BONE,"",
                "red",
                "yellow",
                "green",
                "blue");
        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTab.INSTANCE);
    }
}