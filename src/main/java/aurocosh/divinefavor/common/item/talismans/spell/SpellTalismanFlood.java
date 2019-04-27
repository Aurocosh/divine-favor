package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.wrapper.MaterialPredicate;
import aurocosh.divinefavor.common.lib.wrapper.PredicateWrapper;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanFlood extends ItemSpellTalisman {
    public SpellTalismanFlood(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        List<BlockPos> posList = UtilCoordinates.INSTANCE.getBlocksInSphere(context.pos, ConfigSpells.flood.radius);
        posList = UtilList.select(posList, new MaterialPredicate(world, Material.WATER));
        posList = UtilList.select(posList, new PredicateWrapper<>(world::getBlockState, state -> state.getValue(BlockLiquid.LEVEL) != 0));

        IBlockState state = Blocks.WATER.getDefaultState();
        for (BlockPos pos : posList)
            UtilBlock.replaceBlock(context.player, world, pos, state);
    }
}
