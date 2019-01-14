package aurocosh.divinefavor.common.muliblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiblockController {
    World getWorld();
    MultiBlockInstance getMultiblockInstance();

    void multiblockDeconstructed();
    void multiblockDamaged(EntityPlayer player, World world, BlockPos pos, IBlockState state);
    void tryToFormMultiBlock();
}
