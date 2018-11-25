package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.Spell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellIgnition extends Spell {
    public SpellIgnition() {
        super(LibSpellNames.IGNITION);
    }

    @Override
    protected boolean performAction(SpellContext context) {
        //if(context.playerIn.getEntityWorld().isRemote)
        //    return true;

        BlockPos pos = context.pos;
        World world = context.worldIn;

        IBlockState state = world.getBlockState(pos);
        if(state.getBlock().isAir(state, world, pos) || state.getBlock().isReplaceable(world, pos))
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        else {
            pos = pos.up();
            state = world.getBlockState(pos);
            if(state.getBlock().isAir(state, world, pos) || state.getBlock().isReplaceable(world, pos))
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        }

        return true;
    }

    @Override
    protected boolean claimCost(SpellContext context) {
        return true;
    }
}
