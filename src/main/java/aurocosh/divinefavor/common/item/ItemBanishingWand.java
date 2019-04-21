package aurocosh.divinefavor.common.item;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMainTabOrder;
import aurocosh.divinefavor.common.item.base.ModItem;

public class ItemBanishingWand extends ModItem {
    public ItemBanishingWand() {
        super("banishing_wand","banishing_wand", ConstMainTabOrder.TOOLS);
        setMaxStackSize(1);
        setCreativeTab(DivineFavor.TAB_MAIN);
    }
}