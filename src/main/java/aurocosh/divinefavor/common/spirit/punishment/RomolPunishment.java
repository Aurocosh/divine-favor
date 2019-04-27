package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RomolPunishment extends SpiritPunishment {
    public static final int BLOCK_SEARCH_LIMIT = 60;

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        destroyRandomBlocks(player, world, instance.multiBlockOrigin);
        breakRandomBlocks(player, world, instance.multiBlockOrigin);
    }

    private void destroyRandomBlocks(EntityPlayer player, World world, BlockPos center) {
        int radius = ConfigPunishments.romol.blockBreakingRadius;
        int blocksToDestroy = ConfigPunishments.romol.blocksToDestroy.random();
        for (int i = 0; i < blocksToDestroy; i++) {
            BlockPos blockPos = UtilCoordinates.INSTANCE.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT, pos -> !world.isAirBlock(pos));
            if (blockPos != null)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, blockPos, false, false, false);
        }
    }

    private void breakRandomBlocks(EntityPlayer player, World world, BlockPos center) {
        int radius = ConfigPunishments.romol.blockBreakingRadius;
        int blocksToBreak = ConfigPunishments.romol.blocksToBreak.random();
        for (int i = 0; i < blocksToBreak; i++) {
            BlockPos blockPos = UtilCoordinates.INSTANCE.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT, pos -> !world.isAirBlock(pos));
            if (blockPos != null)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, blockPos, true, false, false);
        }
    }
}
