package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited
import aurocosh.divinefavor.common.potions.common.ModPotions
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
class PotionToadicJump : ModPotionToggleLimited("toadic_jump", true, 0x7FB8A4) {

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return false
    }

    companion object {
        @SubscribeEvent
        fun onPlayerJump(event: LivingEvent.LivingJumpEvent) {
            val entity = event.entityLiving as? EntityPlayer ?: return
            if (!entity.isPotionActive(ModPotions.toadic_jump))
                return

            entity.motionY += ConfigSpells.toadicJump.jumpBoost.toDouble()
            if (entity.world.isRemote)
                return

            val spiritData = entity.divineCustomData.spiritData
            val talisman = ModPotions.toadic_jump.talisman
            val spirit = talisman.spirit
            if (spiritData.consumeFavor(spirit.id, talisman.favorCost))
                MessageSyncFavor(spirit, spiritData).sendTo(entity)
        }
    }
}
