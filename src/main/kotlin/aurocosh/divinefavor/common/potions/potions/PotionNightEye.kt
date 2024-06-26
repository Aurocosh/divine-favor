package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.util.UtilTick
import aurocosh.divinefavor.common.util.UtilWorld
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.world.EnumSkyBlock
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionNightEye : ModPotionToggle("night_eye", 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        val world = livingBase.world
        if (world.isRemote)
            return
        if (!TICK_COUNTER.isFinished)
            return

        val pos = livingBase.position
        val lightLevel = UtilWorld.getLightLevel(world, pos)
        if (lightLevel <= ConfigSpell.nightEye.tolerableLightLevel)
            livingBase.addPotionEffect(PotionEffect(MobEffects.NIGHT_VISION, ConfigSpell.nightEye.nightVisionDuration))
        else
            livingBase.addPotionEffect(PotionEffect(MobEffects.BLINDNESS, ConfigSpell.nightEye.blindnessDuration))
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        val TICK_RATE = UtilTick.secondsToTicks(1f)
        private val TICK_COUNTER = LoopedCounter(TICK_RATE)

        @SubscribeEvent
        @JvmStatic
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                TICK_COUNTER.tick()
        }
    }
}
