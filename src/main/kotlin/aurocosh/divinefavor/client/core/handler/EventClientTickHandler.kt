package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.ITemplateContainer
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.network.message.sever.template.MessageRequestTemplate
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
@SideOnly(Side.CLIENT)
object EventClientTickHandler {
    private val refreshPeriod = UtilTick.secondsToTicks(60f)
    private val connectRefreshDelay = UtilTick.secondsToTicks(10f)

    private val mc = Minecraft.getMinecraft()
    private val refreshCounter = LoopedCounter(refreshPeriod);

    @SubscribeEvent
    @JvmStatic
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (!refreshCounter.tick())
            return

        val player = mc.player ?: return

        val templateSavedData = player.world[TemplateData]
        val templatesInInventory = player.getInventoryCapability().asSequence()
                .mapPairs { it.item as? ITemplateContainer }
                .filterNotNull()
                .map { (stack, container) -> container.getTemplatesIds(stack) }
                .flatten()
                .filter { !templateSavedData.contains(it) }

        val currentTemplate = sequenceOf(player.divinePlayerData.templateData.currentTemplate)
        val templatesToSync = (templatesInInventory + currentTemplate).toList()

        MessageRequestTemplate(templatesToSync).send()
    }

    @SubscribeEvent
    @JvmStatic
    fun onClientConnect(event: FMLNetworkEvent.ClientConnectedToServerEvent) {
        refreshCounter.count = refreshPeriod - connectRefreshDelay
    }
}
