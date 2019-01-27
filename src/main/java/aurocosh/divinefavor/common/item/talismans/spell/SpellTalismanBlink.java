package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;

public class SpellTalismanBlink extends ItemSpellTalisman {
    private final boolean isSafe;
    private final double blinkDistance;

    public SpellTalismanBlink(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options, boolean isSafe, double blinkDistance) {
        super(name, favor, favorCost, options);
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
