package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class SpellTalismanEtherealLight extends ItemSpellTalisman {
    public SpellTalismanEtherealLight(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);
        return context.world.isAirBlock(pos);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        BlockPos pos = context.pos.offset(context.facing);
        context.world.setBlockState(pos, ModBlocks.ethereal_light.getDefaultState());
    }
}
