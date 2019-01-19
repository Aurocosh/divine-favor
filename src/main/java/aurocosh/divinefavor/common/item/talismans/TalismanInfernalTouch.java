package aurocosh.divinefavor.common.item.talismans;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TalismanInfernalTouch extends ItemTalisman {
    private static final int USES = 10;

    public TalismanInfernalTouch() {
        super("infernal_touch", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
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
