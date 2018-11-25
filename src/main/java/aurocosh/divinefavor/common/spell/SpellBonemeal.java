package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.Spell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SpellBonemeal extends Spell {
    public SpellBonemeal() {
        super(LibSpellNames.BONEMEAL);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        BlockPos pos = context.pos;
        if(!bonemeal(context.playerIn, pos))
            bonemeal(context.playerIn, pos.down());

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }

    public boolean bonemeal(EntityPlayer player, BlockPos pos) {
        boolean success = ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 15), player.getEntityWorld(), pos);
        if(success)
            player.getEntityWorld().playEvent(2005, pos, 0);
        return success;
    }
}
