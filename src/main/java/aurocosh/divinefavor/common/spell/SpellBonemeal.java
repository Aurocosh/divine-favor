package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SpellBonemeal extends ModSpell {
    public SpellBonemeal() {
        super(SpellType.BONEMEAL);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.player.getEntityWorld().isRemote)
        //    return true;

        BlockPos pos = context.pos;
        if(!bonemeal(context.player, pos))
            bonemeal(context.player, pos.down());

        return true;
    }

    public boolean bonemeal(EntityPlayer player, BlockPos pos) {
        boolean success = ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 15), player.getEntityWorld(), pos);
        if(success)
            player.getEntityWorld().playEvent(2005, pos, 0);
        return success;
    }
}
