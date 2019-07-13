package aurocosh.divinefavor.common.event_handlers

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraftforge.event.entity.player.EntityItemPickupEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@EventBusSubscriber(modid = DivineFavor.MOD_ID)
object PickupEventHandler {
    @SubscribeEvent
    fun catchBlockDrops(event: BlockEvent.HarvestDropsEvent) {
        val player = event.harvester ?: return
        val hand = UtilPlayer.getHandWithItem(player) { it is IBlockCatcher } ?: return
        val stack = player.getHeldItem(hand)
        val blockCatcher = stack.item as IBlockCatcher
        blockCatcher.catchDrops(stack, stack, event)
    }

    @SubscribeEvent
    fun onPickup(event: EntityItemPickupEvent) {
        val itemStack = event.item.item
        UtilPlayer.addGooToContainers(event.entityPlayer, itemStack)
    }
}

