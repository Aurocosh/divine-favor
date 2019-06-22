package aurocosh.divinefavor.common.event_handlers

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent
import net.minecraftforge.event.entity.living.LootingLevelEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object EntityEventHandler {
    @SubscribeEvent
    fun onLootingLevelEvent(event: LootingLevelEvent) {
        event.lootingLevel = event.lootingLevel + event.entityLiving.divineLivingData.extraLootingData.extraLooting
    }

    @SubscribeEvent
    fun onLivingDropsEvent(event: LivingDropsEvent) {
        if (event.entityLiving.divineLivingData.extraLootingData.isLootDenied)
            event.isCanceled = true
    }
    @SubscribeEvent
    fun onLivingExperienceDropEvent(event: LivingExperienceDropEvent) {
        if (event.entityLiving.divineLivingData.extraLootingData.isExperienceDenied)
            event.isCanceled = true
    }
}