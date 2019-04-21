package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.function.Supplier;

public class SpellTalismanAirReplace extends ItemSpellTalisman {
    private final Supplier<Block> blockProvider;

    // cant pass blockDirectly they are not initialized yet
    public SpellTalismanAirReplace(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options, Supplier<Block> blockProvider) {
        super(name, spirit, favorCost, options);
        this.blockProvider = blockProvider;
    }

    @Override
    protected boolean validate(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);
        return UtilBlock.isAirOrReplaceable(context.world, pos);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);
        UtilBlock.replaceBlock(context.player, context.world, pos, blockProvider.get().getDefaultState());
    }
}
