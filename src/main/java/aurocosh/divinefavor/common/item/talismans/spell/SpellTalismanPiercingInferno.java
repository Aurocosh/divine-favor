package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanPiercingInferno extends ItemSpellTalisman {
    public static int MAX_PIERCE_SHAPE_SIZE = 25;
    public static int MAX_PIERCE_DEPTH = 10;
    public static int CHANCE_TO_IGNITE = 20;

    public SpellTalismanPiercingInferno(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        List<BlockPos> piercingShape = UtilCoordinates.getNeighboursWithSameExposedFace(context.pos, context.world, context.facing, MAX_PIERCE_SHAPE_SIZE);
        BlockPos shift = new BlockPos(context.facing.getOpposite().getDirectionVec());
        for (int i = 0; i < MAX_PIERCE_DEPTH; i++) {
            for (BlockPos pos : piercingShape) {
                Block block = UtilRandom.rollDice(CHANCE_TO_IGNITE) ? Blocks.FIRE : Blocks.AIR;
                context.world.setBlockState(pos, block.getDefaultState());
            }
            piercingShape = UtilCoordinates.shiftCoordinates(piercingShape, shift);
        }
    }
}
