package aurocosh.divinefavor.common.event_handlers

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talisman_tools.spell_pick.ItemSpellPick
import aurocosh.divinefavor.common.lib.interfaces.IBlockCatcher
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@EventBusSubscriber(modid = DivineFavor.MOD_ID)
object BreakEventHandler {
    @SubscribeEvent
    fun catchBlockDrops(event: BlockEvent.HarvestDropsEvent) {
        val player = event.harvester ?: return
        val hand = UtilPlayer.getHandWithItem(player) { it is IBlockCatcher } ?: return
        val stack = player.getHeldItem(hand)
        val blockCatcher = stack.item as IBlockCatcher
        blockCatcher.catchDrops(stack, stack, event)
    }

    @SubscribeEvent
    fun breakSpeed(event: PlayerEvent.BreakSpeed) {
        val stack = event.entityPlayer.inventory.getCurrentItem()
        val item = stack.item
        if (item is ItemSpellPick)
            item.getMiningSpeed(stack, event)
    }
}

