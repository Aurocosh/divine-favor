package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.tasks.BlockBreakingTask;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

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
        List<Vector3i> start = Collections.singletonList(new Vector3i(context.pos));
        List<BlockPos> logs = UtilCoordinates.floodFill(start, UtilVector3i.getNeighbourDirections(), blockPos -> isWood(world, blockPos), ConfigSpells.fellTree.maxLogsBroken);
        if (logs.isEmpty())
            return logs;

        start = Vector3i.convertPos(logs);
        UtilList.Predicate<BlockPos> predicate = blockPos -> {
            IBlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            return block.isWood(world, blockPos) || block.isLeaves(blockState, world, blockPos);
        };
        List<BlockPos> leaves = UtilCoordinates.floodFill(start, UtilVector3i.getNeighbourDirections(), predicate, ConfigSpells.fellTree.minLeafCount);
        if (leaves.size() < ConfigSpells.fellTree.minLeafCount)
            logs.clear();
        return logs;
    }

    private static boolean isWood(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().isWood(world, blockPos);
    }
}
