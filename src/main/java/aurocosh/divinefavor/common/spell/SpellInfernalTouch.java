package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpellInfernalTouch extends ModSpell {
    public SpellInfernalTouch() {
        super("infernal_touch");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos pos = context.pos;
        World world = context.world;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Material material = state.getMaterial();

        IBlockState newState = null;
        if(material == Material.ROCK || block == Blocks.NETHERRACK)
            newState = Blocks.LAVA.getDefaultState();
        else if(material == Material.WOOD)
            newState = Blocks.COAL_BLOCK.getDefaultState();
        else if(material == Material.GRASS || material == Material.GROUND)
            newState = Blocks.GRAVEL.getDefaultState();
        else if(block == Blocks.GRAVEL)
            newState = Blocks.SAND.getDefaultState();
        else if(material == Material.SAND)
            newState = Blocks.GLASS.getDefaultState();
        if(newState != null)
            world.setBlockState(pos, newState);
    }
}
