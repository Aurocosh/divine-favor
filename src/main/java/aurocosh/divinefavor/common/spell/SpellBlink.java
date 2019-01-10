package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellBlink extends ModSpell {
    public final double BLINK_DISTANCE = 5;

    private final boolean isSafe;
    private final double blinkDistance;

    public SpellBlink(double blinkDistance, boolean isSafe) {
        this.isSafe = isSafe;
        this.blinkDistance = blinkDistance;
    }

    @Override
    public boolean isConsumeCharge(SpellContext context) {
        BlockPos target = getBlinkTarget(context.player);
        return !isSafe || checkSafety(context.world, target);
    }

    @Override
    protected void performActionServer(SpellContext context) {
        BlockPos target = getBlinkTarget(context.player);
        if (!isSafe || checkSafety(context.world, target))
            UtilEntity.teleport(context.player, target.down());
    }

    private BlockPos getBlinkTarget(EntityPlayer player) {
        Vec3d pos = player.getPositionVector();
        Vec3d look = player.getLookVec();
        return new BlockPos(pos.add(look.scale(blinkDistance)));
    }

    private boolean checkSafety(World world, BlockPos pos){
        return world.isAirBlock(pos) && world.isAirBlock(pos.up());
    }
}
