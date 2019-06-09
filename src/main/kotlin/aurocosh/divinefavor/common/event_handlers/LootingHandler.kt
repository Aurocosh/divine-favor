package aurocosh.divinefavor.common.event_handlers

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LootingLevelEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object LootingHandler {
    @SubscribeEvent
    fun onLootingLevelEvent(event: LootingLevelEvent) {
        val target = event.entity
        if (target is EntityLivingBase)
            event.lootingLevel = event.lootingLevel + target.divineLivingData.extraLootingData.extraLooting
    }
}