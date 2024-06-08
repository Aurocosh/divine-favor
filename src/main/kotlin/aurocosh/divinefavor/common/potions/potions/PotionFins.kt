package aurocosh.divinefavor.common.potions.potions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class PotionFins : ModPotion("fins", 0x7FB8A4) {
    companion object {
        private val SPEED_MODIFIER = 0.14f

        @SubscribeEvent
        @JvmStatic
        fun onEntityUpdate(event: LivingEvent.LivingUpdateEvent) {
            val entity = event.entityLiving ?: return
            if (!entity.isPotionActive(ModPotions.fins))
                return
            if (!entity.isInWater)
                return
            UtilEntity.addVelocity(entity, SPEED_MODIFIER)
        }
    }
}
