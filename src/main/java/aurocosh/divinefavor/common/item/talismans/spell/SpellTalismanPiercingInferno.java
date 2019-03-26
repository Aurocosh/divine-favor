package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
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
    public SpellTalismanPiercingInferno(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        List<BlockPos> piercingShape = UtilCoordinates.getNeighboursWithSameExposedFace(context.pos, context.world, context.facing, ConfigSpells.piercingInferno.maxPierceShapeSize);
        BlockPos shift = new BlockPos(context.facing.getOpposite().getDirectionVec());
        for (int i = 0; i < ConfigSpells.piercingInferno.maxPierceDepth; i++) {
            for (BlockPos pos : piercingShape) {
                Block block = UtilRandom.rollDice(ConfigSpells.piercingInferno.chanceToIgnite) ? Blocks.FIRE : Blocks.AIR;
                context.world.setBlockState(pos, block.getDefaultState());
            }
            piercingShape = UtilCoordinates.shiftCoordinates(piercingShape, shift);
        }
    }
}
