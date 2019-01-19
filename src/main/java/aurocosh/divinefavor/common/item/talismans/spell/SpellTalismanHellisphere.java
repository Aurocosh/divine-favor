package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SpellTalismanHellisphere extends ItemSpellTalisman {
    public static int EFFECT_RADIUS = 6;
    private static final int USES = 10;

    public SpellTalismanHellisphere() {
        super("hellisphere", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        IBlockState state = Blocks.LAVA.getDefaultState();
        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(context.pos, 5);
        for (BlockPos pos : posList)
            if (!world.isAirBlock(pos))
                world.setBlockState(pos, state);
    }
}
