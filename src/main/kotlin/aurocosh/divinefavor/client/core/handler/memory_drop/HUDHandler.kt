package aurocosh.divinefavor.client.core.handler.memory_drop

//@SideOnly(Side.CLIENT)
//@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
//object HUDHandler {
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    fun renderWorldLastEvent(lastEvent: RenderWorldLastEvent) {
//        val player = DivineFavor.proxy.clientPlayer
//        val hand = UtilPlayer.getHandWithItem(player) { it is ItemMemoryDrop } ?: return
//        val stack = player.getHeldItem(hand)
//
//        if(!stack.isPropertySet(ItemMemoryDrop.uuid))
//            return
//
//        val traceResult = UtilEntity.getBlockPlayerLookingAt(player, ConfigGeneral.talismanCastDistance.toDouble())
//                ?: return
//
//        val uuid = stack.get(ItemMemoryDrop.uuid)
//        BlockTemplateRendering.render(lastEvent, player, uuid, traceResult.blockPos)
//    }
//}