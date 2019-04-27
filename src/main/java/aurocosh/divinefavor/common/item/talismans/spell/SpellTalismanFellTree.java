package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.tasks.BlockBreakingTask;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class SpellTalismanFellTree extends ItemSpellTalisman {
    public SpellTalismanFellTree(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        List<BlockPos> logs = detectTree(context);
        if (!logs.isEmpty()) {
            ItemStack talisman = context.player.getHeldItem(context.hand);
            new BlockBreakingTask(logs, context.player, talisman, ConfigSpells.fellTree.breakingSpeed).start();
        }
    }

    private List<BlockPos> detectTree(TalismanContext context) {
        World world = context.world;
        List<BlockPos> start = Collections.singletonList(context.pos);
        List<BlockPos> logs = UtilCoordinates.INSTANCE.floodFill(start, BlockPosConstants.DIRECT_NEIGHBOURS, blockPos -> isWood(world, blockPos), ConfigSpells.fellTree.maxLogsBroken);
        if (logs.isEmpty())
            return logs;

        Predicate<BlockPos> predicate = blockPos -> {
            IBlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            return block.isWood(world, blockPos) || block.isLeaves(blockState, world, blockPos);
        };
        List<BlockPos> leaves = UtilCoordinates.INSTANCE.floodFill(logs, BlockPosConstants.DIRECT_NEIGHBOURS, predicate::test, ConfigSpells.fellTree.minLeafCount);
        if (leaves.size() < ConfigSpells.fellTree.minLeafCount)
            logs.clear();
        return logs;
    }

    private static boolean isWood(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().isWood(world, blockPos);
    }
}
