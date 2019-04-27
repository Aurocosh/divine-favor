package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionHovering extends ModPotionToggle {
    public PotionHovering() {
        super("hovering", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            livingBase.removePotionEffect(ModPotions.hovering);
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        if (livingBase instanceof EntityPlayer)
            UtilPlayer.setAllowFlying((EntityPlayer) livingBase, false);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        World world = livingBase.world;
        if (world.isRemote)
            return;

        BlockPos currentPosition = livingBase.getPosition();
        BlockPos previousPosition = UtilEntity.getPreviousPosition(livingBase);

        boolean allowFlying = currentPosition.equals(previousPosition);
        if (!allowFlying) {
            BlockPos pos = UtilCoordinates.INSTANCE.findPosition(livingBase.getPosition(), 10, (blockPos) -> world.getBlockState(blockPos).isSideSolid(world, blockPos, EnumFacing.UP), BlockPos::down);
            allowFlying = pos != null;
        }

        UtilPlayer.setAllowFlying((EntityPlayer) livingBase, allowFlying);
        if (!allowFlying)
            livingBase.removePotionEffect(ModPotions.hovering);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
