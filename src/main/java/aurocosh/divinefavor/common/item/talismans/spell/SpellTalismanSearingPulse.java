package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilBlockPos;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class SpellTalismanSearingPulse extends ItemSpellTalisman {
    private final static int CYCLE_LIMIT = 300;

    public SpellTalismanSearingPulse(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Queue<BlockPos> nodesToVisit = new ArrayDeque<>();
        Set<BlockPos> visitedNodes = new HashSet<>();
        nodesToVisit.add(context.pos);

        int cycleLimit = CYCLE_LIMIT;
        int blocksToSmelt = UtilRandom.nextInt(ConfigSpells.searingPulse.minBlocksToSmelt, ConfigSpells.searingPulse.maxBlocksToSmelt);
        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && blocksToSmelt-- > 0) {
            BlockPos nextNode = nodesToVisit.remove();
            visitedNodes.add(nextNode);

            if (UtilRandom.rollDiceFloat(ConfigSpells.searingPulse.chanceToCreateFire)) {
                UtilBlock.ignite(context.player, context.world, nextNode);
                continue;
            }
            if (!UtilBlock.smeltBlock(context.player, context.world, nextNode))
                continue;

            int neighboursToAdd = UtilRandom.nextInt(ConfigSpells.searingPulse.minNeighboursToAdd, ConfigSpells.searingPulse.maxNeighboursToAdd);
            List<BlockPos> dirs = UtilBlockPos.getRandomDirections(neighboursToAdd);
            for (BlockPos dir : dirs) {
                BlockPos node = nextNode.add(dir);
                if (!visitedNodes.contains(node))
                    nodesToVisit.add(node);
            }
        }
    }
}
