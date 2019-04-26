package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.tasks.BlockProcessingTask;
import aurocosh.divinefavor.common.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SpellTalismanPiercingInferno extends ItemSpellTalisman {
    public SpellTalismanPiercingInferno(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EnumFacing facing = context.facing;

        int blocksToBreak = context.player.isSneaking() ? ConfigSpells.piercingInferno.blocksToBreakWeak : ConfigSpells.piercingInferno.blocksToBreakNormal;

        List<BlockPos> expansionDirs = new ArrayList<>(BlockPosConstants.DIRECT_NEIGHBOURS);
        BlockPos shift = new BlockPos(facing.getOpposite().getDirectionVec());
        expansionDirs.remove(shift);
        expansionDirs.remove(UtilBlockPos.inverse(shift));

        Predicate<BlockPos> predicate = pos -> {
            IBlockState state = world.getBlockState(pos);
            if (!state.isSideSolid(world, pos, facing))
                return false;
            return world.isAirBlock(pos.offset(facing));
        };

        List<BlockPos> piercingShape = UtilCoordinates.floodFill(Collections.singletonList(context.pos), expansionDirs, predicate, blocksToBreak);
        int shapeSize = piercingShape.size();
        blocksToBreak -= shapeSize;

        List<BlockPos> blocksToRemove = new ArrayList<>();
        while (blocksToBreak > 0) {
            for (int i = 0; blocksToBreak-- > 0 && i < shapeSize; i++)
                blocksToRemove.add(piercingShape.get(i));
            piercingShape = UtilList.process(piercingShape, pos -> pos.add(shift));
        }

        Consumer<BlockPos> processor = pos -> {
            Block block = UtilRandom.rollDice(ConfigSpells.piercingInferno.chanceToIgnite) ? Blocks.FIRE : Blocks.AIR;
            UtilBlock.replaceBlock(context.player, context.world, pos, block.getDefaultState());
        };
        new BlockProcessingTask(blocksToRemove, world, 1, processor).start();
    }
}
