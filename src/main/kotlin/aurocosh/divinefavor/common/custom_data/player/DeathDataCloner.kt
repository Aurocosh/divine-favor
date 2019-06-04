package aurocosh.divinefavor.common.custom_data.player

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object DeathDataCloner {
    @SubscribeEvent
    fun clonePlayer(event: PlayerEvent.Clone) {
        val originalData = event.original.divinePlayerData
        val cloneData = event.entityPlayer.divinePlayerData

        cloneData.spiritData.deserializeContracts(originalData.spiritData.serializeContracts())
        cloneData.spiritData.setFavorValues(originalData.spiritData.getFavorValues())

        cloneData.grudgeData.mobTypeId = originalData.grudgeData.mobTypeId
        cloneData.interactionData.lastClickedPositions = originalData.interactionData.lastClickedPositions
        cloneData.pearlCrumbsData.allPositions = originalData.pearlCrumbsData.allPositions
    }
}