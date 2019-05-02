package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.world.EnumSkyBlock
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionNightEye : ModPotionToggle("night_eye", true, 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val world = livingBase.world
        if (world.isRemote)
            return
        if (!TICK_COUNTER.isFinished)
            return

        val pos = livingBase.position
        val skyLightSub = world.calculateSkylightSubtracted(1.0f)
        val lightBlock = world.getLightFor(EnumSkyBlock.BLOCK, pos)
        val lightSky = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub
        if (Math.max(lightBlock, lightSky) <= ConfigSpells.nightEye.tolerableLightLevel)
            livingBase.addPotionEffect(PotionEffect(MobEffects.NIGHT_VISION, ConfigSpells.nightEye.nightVisionDuration))
        else
            livingBase.addPotionEffect(PotionEffect(MobEffects.BLINDNESS, ConfigSpells.nightEye.blindnessDuration))
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        val TICK_RATE = UtilTick.secondsToTicks(1f)
        private val TICK_COUNTER = LoopedCounter(TICK_RATE)

        @SubscribeEvent
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                TICK_COUNTER.tick()
        }
    }
}
