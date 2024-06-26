package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionCrawlingMist : ModPotion("crawling_mist", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.crawlingMistData.mistOrigin = livingBase.getPosition()
        else
            livingBase.removePotionEffect(ModCurses.crawling_mist)
    }

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase.world.isRemote)
            return
        if (livingBase !is EntityPlayer)
            return
        if (!CURE_COUNTER.isFinished)
            return
        val mistOrigin = livingBase.divinePlayerData.crawlingMistData.mistOrigin
        val distanceSq = mistOrigin.distanceSq(livingBase.getPosition())
        if (distanceSq > CURE_DISTANCE_SQ)
            livingBase.removePotionEffect(ModCurses.crawling_mist)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val FRAMES_TO_INIT_FOG = 5
        private val CURE_DISTANCE_SQ = ConfigCurses.crawlingMist.cureDistance * ConfigCurses.crawlingMist.cureDistance
        private val CURE_COUNTER = LoopedCounter(ConfigCurses.crawlingMist.cureRate)
        private var initFrames = FRAMES_TO_INIT_FOG

        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        @JvmStatic
        fun onFogColor(event: EntityViewRenderEvent.FogColors) {
            if (!isActive(event))
                return
            event.red = 0.7f
            event.green = 0.7f
            event.blue = 0.98f
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        @JvmStatic
        fun onFogDensity(event: EntityViewRenderEvent.FogDensity) {
            if (isActive(event)) {
                if (initFrames-- <= 0) {
                    event.density = 0.85f
                    GlStateManager.setFogStart(ConfigCurses.crawlingMist.fogStart.toFloat())
                    GlStateManager.setFogEnd(ConfigCurses.crawlingMist.fogEnd.toFloat())
                    event.isCanceled = true
                }
            } else
                initFrames = FRAMES_TO_INIT_FOG
        }

        private fun isActive(event: EntityViewRenderEvent): Boolean {
            val entity = event.entity as? EntityPlayer ?: return false
            return entity.isPotionActive(ModCurses.crawling_mist)
        }

        @SubscribeEvent
        @JvmStatic
        fun serverTickEnd(event: TickEvent.ServerTickEvent) {
            if (event.phase == TickEvent.Phase.END)
                CURE_COUNTER.tick()
        }
    }
}
