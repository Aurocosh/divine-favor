package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumHand;

public class SpellTalismanBlazingPalm extends ItemSpellTalisman {
    private static final int USES = 3;

    public SpellTalismanBlazingPalm() {
        super("blazing_palm", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EnumHand hand = UtilPlayer.getOtherHand(context.hand);
        EntityPlayer player = context.player;
        ItemStack stack = player.getHeldItem(hand);
        ItemStack resultTemplate = FurnaceRecipes.instance().getSmeltingResult(stack);
        if (resultTemplate.isEmpty())
            return;
        ItemStack result = resultTemplate.copy();
        result.setCount(stack.getCount());

        int slotIndex = UtilPlayer.getHandIndex(player, hand);
        player.inventory.setInventorySlotContents(slotIndex, result);
    }
}
