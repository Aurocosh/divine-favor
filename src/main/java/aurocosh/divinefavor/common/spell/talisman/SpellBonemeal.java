package aurocosh.divinefavor.common.spell.talisman;

import aurocosh.divinefavor.common.spell.talisman.base.ModSpell;
import aurocosh.divinefavor.common.spell.talisman.base.SpellContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SpellBonemeal extends ModSpell {
    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos pos = context.pos;
        if(!bonemeal(context.player, pos))
            bonemeal(context.player, pos.down());
    }

    public boolean bonemeal(EntityPlayer player, BlockPos pos) {
        boolean success = ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, 15), player.getEntityWorld(), pos);
        if(success)
            player.getEntityWorld().playEvent(2005, pos, 0);
        return success;
    }
}
