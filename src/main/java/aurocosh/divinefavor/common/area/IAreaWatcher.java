package aurocosh.divinefavor.common.area;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IAreaWatcher {
    World getWorld();
    WorldArea getArea();

    void blockBroken(World world, BlockPos pos, IBlockState state, EntityPlayer player);
    void blockPlaced(World world, BlockPos pos, IBlockState state, EntityPlayer player);
}
