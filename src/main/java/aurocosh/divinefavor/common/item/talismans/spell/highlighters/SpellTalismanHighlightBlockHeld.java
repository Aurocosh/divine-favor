package aurocosh.divinefavor.common.item.talismans.spell.highlighters;

import aurocosh.divinefavor.common.config.entries.talismans.spell.SenseBlock;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.vecmath.Color3f;
import java.util.EnumSet;

public class SpellTalismanHighlightBlockHeld extends SpellTalismanHighlightBlockPredicated {

    public SpellTalismanHighlightBlockHeld(String name, ModSpirit spirit, EnumSet<SpellOptions> options, Color3f color3f, SenseBlock senseBlock) {
        super(name, spirit, options, color3f, senseBlock, SenseBlockPredicate.BLOCK);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = context.player.getHeldItem(otherHand);
        if (heldItem.isEmpty())
            return false;
        Item item = heldItem.getItem();
        return item instanceof ItemBlock;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        BlockPos playerPosition = player.getPosition();

        EnumHand otherHand = UtilPlayer.getOtherHand(context.hand);
        ItemStack heldItem = player.getHeldItem(otherHand);
        ItemBlock itemBlock = (ItemBlock) heldItem.getItem();
        String blockName = itemBlock.getBlock().getRegistryName().toString();

        highlightBlocks(radius, player, playerPosition, minShift, maxShift, color3f, SenseBlockPredicate.BLOCK, blockName);
    }
}
