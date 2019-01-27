package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanBonemeal extends ItemSpellTalisman {
    public SpellTalismanBonemeal(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
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
