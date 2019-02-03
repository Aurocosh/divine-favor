package aurocosh.divinefavor.common.area;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAreaWatcher {
    World getWorld();
    WorldArea getArea();

    void blockChanged(World world, BlockPos pos, IBlockState state);
}
