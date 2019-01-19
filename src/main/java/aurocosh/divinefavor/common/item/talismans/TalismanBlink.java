package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TalismanBlink extends ItemTalisman {
    public final double BLINK_DISTANCE = 5;

    private final boolean isSafe;
    private final double blinkDistance;

    public TalismanBlink(String name, int startingSpellUses, boolean isSafe, double blinkDistance) {
        super(name, startingSpellUses, true, true);
        this.isSafe = isSafe;
        this.blinkDistance = blinkDistance;
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        BlockPos target = getBlinkTarget(context.player);
        return !isSafe || checkSafety(context.world, target);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
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
