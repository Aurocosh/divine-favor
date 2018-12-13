package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class SpellSearingPulse extends ModSpell {
    private final int MIN_NEIGHBOURS_TO_ADD = 3;
    private final int Max_NEIGHBOURS_TO_ADD = 4;

    private final int MIN_BLOCKS_TO_SMELT = 20;
    private final int MAX_BLOCKS_TO_SMELT = 30;

    private final int CHANCE_TO_CREATE_FIRE = 40;

    private final int CYCLE_LIMIT = 30;

    public SpellSearingPulse() {
        super("searing_pulse");
    }

    @Override
    protected boolean performAction(SpellContext context) {
        assert context.castType == CastType.ITEM_USE_CAST;

        BlockPos pos = context.pos;
        TileEntity entity = context.world.getTileEntity(pos);
        if (entity != null)
            return false;

        Queue<Vector3i> nodesToVisit = new ArrayDeque<>();
        Set<Vector3i> visitedNodes = new HashSet<>();
        nodesToVisit.add(Vector3i.convert(pos));

        int cycleLimit = CYCLE_LIMIT;
        int blocksToSmelt = UtilRandom.nextInt(MIN_BLOCKS_TO_SMELT, MAX_BLOCKS_TO_SMELT);
        while (!nodesToVisit.isEmpty() && cycleLimit-- > 0 && blocksToSmelt-- > 0) {
            Vector3i nextNode = nodesToVisit.remove();
            visitedNodes.add(nextNode);

            if(UtilBlock.isAirOrReplaceable(context.world,pos)){
                boolean setOnFire = UtilRandom.getPercentChance(CHANCE_TO_CREATE_FIRE);
                if(setOnFire)
                    context.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
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
        return true;
    }
}
