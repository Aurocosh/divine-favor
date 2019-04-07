package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.init.Blocks;

import java.util.*;

public class SpellTalismanSearingPulse extends ItemSpellTalisman {
    private final int CYCLE_LIMIT = 300;

    public SpellTalismanSearingPulse(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Queue<Vector3i> nodesToVisit = new ArrayDeque<>();
        Set<Vector3i> visitedNodes = new HashSet<>();
        nodesToVisit.add(Vector3i.convert(context.pos));

        int cycleLimit = CYCLE_LIMIT;
        int blocksToSmelt = UtilRandom.nextInt(ConfigSpells.searingPulse.minBlocksToSmelt, ConfigSpells.searingPulse.maxBlocksToSmelt);
        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && blocksToSmelt-- > 0) {
            Vector3i nextNode = nodesToVisit.remove();
            visitedNodes.add(nextNode);

            if (UtilBlock.isAirOrReplaceable(context.world, context.pos)) {
                boolean setOnFire = UtilRandom.rollDice(ConfigSpells.searingPulse.chanceToCreateFire);
                if (setOnFire)
                    context.world.setBlockState(context.pos, Blocks.FIRE.getDefaultState());
                continue;
            }

            if (!UtilBlock.smeltBlock(context.player, context.world, nextNode.toBlockPos()))
                continue;

            int neighboursToAdd = UtilRandom.nextInt(ConfigSpells.searingPulse.minNeighboursToAdd, ConfigSpells.searingPulse.maxNeighboursToAdd);
            List<Vector3i> dirs = UtilVector3i.getRandomDirections(neighboursToAdd);
            for (Vector3i dir : dirs) {
                Vector3i node = nextNode.add(dir);
                if (!visitedNodes.contains(node))
                    nodesToVisit.add(node);
            }
        }
    }
}
