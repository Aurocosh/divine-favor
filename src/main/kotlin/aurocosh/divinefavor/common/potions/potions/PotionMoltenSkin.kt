package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionMoltenSkin : ModPotionToggle("molten_skin", 0x7FB8A4) {

    override fun performEffect(livingBase: EntityLivingBase, amplifier: Int) {
        if (livingBase !is EntityPlayer)
            return

        val skinData = livingBase.divinePlayerData.moltenSkinData
        if (livingBase.isInLava()) {
            skinData.resetTime()
            return
        } else if (livingBase.isInWater())
            skinData.setMaxTime()

        if (!skinData.tick())
            return

        livingBase.attackEntityFrom(ModDamageSources.frostDamage, ConfigSpell.moltenSkin.freezingDamage)
        skinData.delay()
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        @SideOnly(Side.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
        @JvmStatic
        fun onFogDensity(event: EntityViewRenderEvent.FogDensity) {
            if (isInLavaWithMoltenSkin(event)) {
                GlStateManager.setFog(GlStateManager.FogMode.EXP)
                event.density = 0.2f
                event.isCanceled = true
            }
        }

        @SubscribeEvent
        @JvmStatic
        fun onEntityUpdate(event: LivingEvent.LivingUpdateEvent) {
            val entity = event.entityLiving ?: return
            if (!entity.isPotionActive(ModPotions.molten_skin))
                return
            if (!entity.isInsideOfMaterial(Material.LAVA))
                return
            UtilEntity.addVelocity(entity, ConfigSpell.moltenSkin.speedModifier)
        }

        private fun isInLavaWithMoltenSkin(event: EntityViewRenderEvent): Boolean {
            val entity = event.entity
            return if (event.state.material != Material.LAVA) false
            else (entity as? EntityPlayer)?.isPotionActive(ModPotions.molten_skin) ?: false
        }
    }
}
