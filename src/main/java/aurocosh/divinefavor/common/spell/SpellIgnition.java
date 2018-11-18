package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.api.spell.SpellContext;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class SpellIgnition extends Spell {
    public SpellIgnition() {
        super(LibSpellNames.IGNITION);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        BlockPos pos = context.pos;

        IBlockState state = context.playerIn.getEntityWorld().getBlockState(pos);
        if(state.getBlock().isAir(state, context.playerIn.getEntityWorld(), pos) || state.getBlock().isReplaceable(context.playerIn.getEntityWorld(), pos))
            context.playerIn.getEntityWorld().setBlockState(pos, Blocks.FIRE.getDefaultState());
        else {
            pos = pos.up();
            state = context.playerIn.getEntityWorld().getBlockState(pos);
            if(state.getBlock().isAir(state, context.playerIn.getEntityWorld(), pos) || state.getBlock().isReplaceable(context.playerIn.getEntityWorld(), pos))
                context.playerIn.getEntityWorld().setBlockState(pos, Blocks.FIRE.getDefaultState());
        }

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}
