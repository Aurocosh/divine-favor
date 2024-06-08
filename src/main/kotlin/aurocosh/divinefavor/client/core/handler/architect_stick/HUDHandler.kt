package aurocosh.divinefavor.client.core.handler.architect_stick

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.block_ovelay.BoxRendering
import aurocosh.divinefavor.common.item.tools.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.lib.extensions.hasKey
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    @JvmStatic
    fun renderWorldLastEvent(event: RenderWorldLastEvent) {
        val player = DivineFavor.proxy.clientPlayer
        val stack = player.heldItemMainhand
        if (stack.item !is ItemMysticArchitectStick)
            return

        if (!stack.hasKey(ItemMysticArchitectStick.TAG_POS_FIRST, ItemMysticArchitectStick.TAG_POS_SECOND))
            return

        val compound = stack.compound
        val startPos = compound.getBlockPos(ItemMysticArchitectStick.TAG_POS_FIRST)
        val endPos = compound.getBlockPos(ItemMysticArchitectStick.TAG_POS_SECOND)

        BoxRendering.render(event, player, startPos, endPos)
    }
}