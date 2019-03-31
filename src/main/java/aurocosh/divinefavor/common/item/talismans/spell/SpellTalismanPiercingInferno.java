package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.tasks.BlockProcessingTask;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SpellTalismanPiercingInferno extends ItemSpellTalisman {
    public SpellTalismanPiercingInferno(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        int blocksToBreak = context.player.isSneaking() ? ConfigSpells.piercingInferno.blocksToBreakWeak : ConfigSpells.piercingInferno.blocksToBreakNormal;
        List<BlockPos> piercingShape = UtilCoordinates.getNeighboursWithSameExposedFace(context.pos, context.world, context.facing, blocksToBreak);
        blocksToBreak -= piercingShape.size();

        BlockPos shift = new BlockPos(context.facing.getOpposite().getDirectionVec());
        List<BlockPos> blocksToRemove = new ArrayList<>();

        while (blocksToBreak > 0) {
            for (int i = 0; i < piercingShape.size() && blocksToBreak-- > 0; i++) {
                BlockPos pos = piercingShape.get(i);
                blocksToRemove.add(new BlockPos(pos));
            }
            piercingShape = UtilCoordinates.shiftCoordinates(piercingShape, shift);
        }

        UtilList.Processor<BlockPos> processor = pos -> {
            Block block = UtilRandom.rollDice(ConfigSpells.piercingInferno.chanceToIgnite) ? Blocks.FIRE : Blocks.AIR;
            context.world.setBlockState(pos, block.getDefaultState());
        };
        MinecraftForge.EVENT_BUS.register(new BlockProcessingTask(blocksToRemove, context.world, 1, processor));
    }
}
