package aurocosh.divinefavor.client.core.handler.architect_stick

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.item.mystic_architect_stick.ArchitectStickMode
import aurocosh.divinefavor.common.item.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.input.Keyboard

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = [Side.CLIENT])
object MouseHandler {
    @SubscribeEvent
    fun onMouseEvent(event: MouseEvent) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return
        val player = DivineFavor.proxy.clientPlayer
        if (!player.isSneaking)
            return
        val dWheel = event.dwheel
        if (dWheel == 0)
            return

        val stack = UtilPlayer.getItemInHand(player) { it is ItemMysticArchitectStick }
        if (stack.isEmpty)
            return

        val compound = stack.compound
        var mode = compound.getInteger(ItemMysticArchitectStick.TAG_CURRENT_MODE)
        if (dWheel < 0)
            mode--
        else
            mode++
        mode = ArchitectStickMode.clampModeIndex(mode)
        compound.setInteger(ItemMysticArchitectStick.TAG_CURRENT_MODE, mode)
        val stickMode = ArchitectStickMode.VALUES[mode]
        player.sendMessage(TextComponentString("Selected mode: " + stickMode.description))
        event.isCanceled = true
    }
}