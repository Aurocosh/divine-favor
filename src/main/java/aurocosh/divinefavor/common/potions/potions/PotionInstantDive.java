package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

public class PotionInstantDive extends ModPotionToggle {
    public PotionInstantDive() {
        super("instant_dive", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        BlockPos pos = livingBase.getPosition();

        if (!UtilBlock.isLiquid(livingBase.world.getBlockState(pos).getBlock()))
            return;
        if (livingBase.isSneaking())
            return;

        livingBase.motionY = -ConfigSpells.instandDive.force;
        livingBase.setAIMoveSpeed(0.1F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
