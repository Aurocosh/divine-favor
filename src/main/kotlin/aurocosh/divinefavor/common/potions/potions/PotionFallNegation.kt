package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncPotionCharge
import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.living.LivingFallEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionFallNegation : ModPotionCharge("fall_negation", 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {
        @SubscribeEvent
        fun onPlayerLand(event: LivingFallEvent) {
            val entity = event.entityLiving as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.fall_negation))
                return
            if (entity.world.isRemote)
                return
            if (event.distance <= 2)
                return

            event.damageMultiplier = 0f
            entity.fallDistance = 0f
            //event.setCanceled(true);

            val effectCharge = (entity.getActivePotionEffect(ModPotions.fall_negation) as ModEffectCharge?) ?: return
            val charges = effectCharge.consumeCharge()
            MessageSyncPotionCharge(ModPotions.fall_negation, charges).sendTo(entity)
        }
    }
}
