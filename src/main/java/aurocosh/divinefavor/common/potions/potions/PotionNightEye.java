package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class PotionNightEye extends ModPotionToggle {
    public static int TOLERABLE_LIGHT_LEVEL = 4;
    public static int BLINDNESS_DURATION = UtilTick.secondsToTicks(12);

    public PotionNightEye() {
        super("night_eye", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        BlockPos pos = livingBase.getPosition();
        World world = livingBase.world;

        int skyLightSub = world.calculateSkylightSubtracted(1.0f);
        int lightBlock = world.getLightFor(EnumSkyBlock.BLOCK, pos);
        int lightSky = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub;
        if (Math.max(lightBlock, lightSky) > TOLERABLE_LIGHT_LEVEL && !livingBase.isPotionActive(MobEffects.BLINDNESS))
            livingBase.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, BLINDNESS_DURATION));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
