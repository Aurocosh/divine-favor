package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilMob
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionMistBlade : ModPotionToggle("mist_blade", true, 0x7FB8A4) {
    companion object {
        private val FRAMES_TO_INIT_FOG = 5
        private var intitFrames = FRAMES_TO_INIT_FOG

        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val source = event.source as? EntityDamageSource ?: return
            val entity = source.trueSource as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.mist_blade))
                return
            event.amount = event.amount + ConfigSpells.mistBlade.extraDamage

            if (!UtilMob.isMobRanged(event.entity))
                return
            event.amount = event.amount + ConfigSpells.mistBlade.extraRangedDamage
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        fun onFogColors(event: EntityViewRenderEvent.FogColors) {
            if (!isMistBladeActive(event))
                return
            event.red = 0.7f
            event.green = 0.7f
            event.blue = 0.98f
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        fun onFogDensity(event: EntityViewRenderEvent.FogDensity) {
            if (isMistBladeActive(event)) {
                if (intitFrames-- <= 0) {
                    event.density = 0.85f
                    GlStateManager.setFogStart(ConfigSpells.mistBlade.fogStart.toFloat())
                    GlStateManager.setFogEnd(ConfigSpells.mistBlade.fogEnd.toFloat())
                    event.isCanceled = true
                }
            } else
                intitFrames = FRAMES_TO_INIT_FOG
        }

        private fun isMistBladeActive(event: EntityViewRenderEvent): Boolean {
            val entity = event.entity as? EntityPlayer ?: return false
            return entity.isPotionActive(ModPotions.mist_blade)
        }
    }
}
