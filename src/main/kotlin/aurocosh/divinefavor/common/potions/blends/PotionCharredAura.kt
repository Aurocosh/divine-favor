package aurocosh.divinefavor.common.potions.blends

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigAura
import aurocosh.divinefavor.common.config.common.ConfigPresence
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModBlendEffects
import aurocosh.divinefavor.common.potions.common.ModBlessings
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionCharredAura : ModPotion("charred_aura", 0x7FB8A4) {

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase is EntityPlayer)
            livingBase.divinePlayerData.charredAuraData.reset()
    }

    companion object {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        @JvmStatic
        fun onBlockPlaced(event: BlockEvent.PlaceEvent) {
            val player = event.player
            if (!player.isPotionActive(ModBlendEffects.charred_aura))
                return
            val state = event.state
            if (!(state.block === Blocks.FIRE))
                return
            if (!ModSpirits.neblaze.isActive)
                return
            val auraData = player.divinePlayerData.charredAuraData
            if (auraData.count()) {
                player.removePotionEffect(ModBlendEffects.charred_aura)
                player.addPotionEffect(ModEffect(ModBlessings.scorching_presence, ConfigPresence.scorchingPresence.duration))
                val playerPosition = player.position
                event.world.newExplosion(player, playerPosition.x.toDouble(), playerPosition.y.toDouble(), playerPosition.z.toDouble(), ConfigAura.charredAura.explosionPower.toFloat(), true, false)
            }
        }
    }
}
