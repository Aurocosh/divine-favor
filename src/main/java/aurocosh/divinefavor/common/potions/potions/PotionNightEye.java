package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PotionNightEye extends ModPotionToggle {
    public static int TOLERABLE_LIGHT_LEVEL = 4;
    public static int BLINDNESS_DURATION = UtilTick.secondsToTicks(12);
    public static int NIGHT_VISION_DURATION = UtilTick.secondsToTicks(20);

    public static final int TICK_RATE = UtilTick.secondsToTicks(1);
    private static final TickCounter TICK_COUNTER = new TickCounter(TICK_RATE);

    public PotionNightEye() {
        super("night_eye", true, 0x7FB8A4);
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        World world = livingBase.world;
        if (world.isRemote)
            return;
        if (!TICK_COUNTER.isFinished())
            return;

        BlockPos pos = livingBase.getPosition();
        int skyLightSub = world.calculateSkylightSubtracted(1.0f);
        int lightBlock = world.getLightFor(EnumSkyBlock.BLOCK, pos);
        int lightSky = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub;
        if (Math.max(lightBlock, lightSky) <= TOLERABLE_LIGHT_LEVEL)
            livingBase.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION));
        else
            livingBase.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, BLINDNESS_DURATION));

    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            TICK_COUNTER.tick();
    }
}
