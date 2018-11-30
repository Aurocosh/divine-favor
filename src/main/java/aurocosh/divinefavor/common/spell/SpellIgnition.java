package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.spell.base.SpellType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class SpellIgnition extends ModSpell {
    public SpellIgnition() {
        super(SpellType.IGNITION, Side.SERVER);
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
}
