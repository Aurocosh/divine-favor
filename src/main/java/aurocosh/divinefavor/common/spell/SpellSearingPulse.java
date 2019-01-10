package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.init.Blocks;

import java.util.*;

public class SpellSearingPulse extends ModSpell {
    private final int MIN_NEIGHBOURS_TO_ADD = 3;
    private final int Max_NEIGHBOURS_TO_ADD = 4;

    private final int MIN_BLOCKS_TO_SMELT = 20;
    private final int MAX_BLOCKS_TO_SMELT = 30;

    private final int CHANCE_TO_CREATE_FIRE = 40;

    private final int CYCLE_LIMIT = 30;

    @Override
    protected void performActionServer(SpellContext context) {
        assert context.castType == CastType.ITEM_USE_CAST;

        Queue<Vector3i> nodesToVisit = new ArrayDeque<>();
        Set<Vector3i> visitedNodes = new HashSet<>();
        nodesToVisit.add(Vector3i.convert(context.pos));

        int cycleLimit = CYCLE_LIMIT;
        int blocksToSmelt = UtilRandom.nextInt(MIN_BLOCKS_TO_SMELT, MAX_BLOCKS_TO_SMELT);
        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && blocksToSmelt-- > 0) {
            Vector3i nextNode = nodesToVisit.remove();
            visitedNodes.add(nextNode);

            if (UtilBlock.isAirOrReplaceable(context.world, context.pos)) {
                boolean setOnFire = UtilRandom.rollDice(CHANCE_TO_CREATE_FIRE);
                if (setOnFire)
                    context.world.setBlockState(context.pos, Blocks.FIRE.getDefaultState());
                continue;
            }

            if (!UtilBlock.smeltBlock(context.player, context.world, nextNode.toBlockPos()))
                continue;

            int neighboursToAdd = UtilRandom.nextInt(MIN_NEIGHBOURS_TO_ADD, Max_NEIGHBOURS_TO_ADD);
            List<Vector3i> dirs = UtilVector3i.getRandomDirections(neighboursToAdd);
            for (Vector3i dir : dirs) {
                Vector3i node = nextNode.add(dir);
                if (!visitedNodes.contains(node))
                    nodesToVisit.add(node);
            }
        }
    }
}
