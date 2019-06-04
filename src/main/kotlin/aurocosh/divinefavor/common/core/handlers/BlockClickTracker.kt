package aurocosh.divinefavor.common.core.handlers

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object BlockClickTracker {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) {
        val world = event.world
        if (world.isRemote)
            return
        val player = event.entityPlayer
        val interactionData = player.divinePlayerData.interactionData
        interactionData.recordLastClickedPosition(event.pos)
    }

    fun wasBlockLeftClicked(player: EntityPlayer, pos: BlockPos): Boolean {
        val interactionData = player.divinePlayerData.interactionData
        return interactionData.wasPositionClicked(pos)
    }
}