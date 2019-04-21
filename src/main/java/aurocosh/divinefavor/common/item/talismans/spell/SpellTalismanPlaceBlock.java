package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanPlaceBlock extends ItemSpellTalisman {
    public SpellTalismanPlaceBlock(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);
        if (!UtilBlock.isAirOrReplaceable(context.world, pos))
            return false;
        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = context.player.getHeldItem(otherHand);
        Item item = heldItem.getItem();
        return item instanceof ItemBlock;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);

        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = context.player.getHeldItem(otherHand);
        UtilBlock.replaceBlock(context.player, context.world, pos, heldItem, otherHand);
    }
}
