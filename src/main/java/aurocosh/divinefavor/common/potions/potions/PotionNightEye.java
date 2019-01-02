package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import static aurocosh.divinefavor.common.util.UtilEntity.tickLiquidWalk;

public class PotionNightEye extends ModPotion {
    public static int TOLERABLE_LIGHT_LEVEL = 4;
    public static int BLINDNESS_DURATION = UtilTick.secondsToTicks(12);

    public PotionNightEye() {
        super("night_eye", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        BlockPos pos = entity.getPosition();
        World world = entity.world;

        int skyLightSub = world.calculateSkylightSubtracted(1.0f);
        int lightBlock = world.getLightFor(EnumSkyBlock.BLOCK, pos);
        int lightSky = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub;
        if (Math.max(lightBlock, lightSky) > TOLERABLE_LIGHT_LEVEL && !entity.isPotionActive(MobEffects.BLINDNESS))
            entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, BLINDNESS_DURATION));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
