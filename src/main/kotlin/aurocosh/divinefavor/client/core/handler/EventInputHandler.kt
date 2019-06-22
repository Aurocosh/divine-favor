package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.talisman_container.TalismanSelectGui
import aurocosh.divinefavor.common.constants.ConstGuiIDs
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object EventInputHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onKeyInput(@Suppress("UNUSED_PARAMETER") event: InputEvent.KeyInputEvent) {
        val mc = Minecraft.getMinecraft()

        if (KeyBindings.talismanSelect.isKeyDown) {
            if (UtilPlayer.getHandWithItem(mc.player) { it is ITalismanToolContainer } != null)
                mc.displayGuiScreen(TalismanSelectGui.INSTANCE)
        } else if (KeyBindings.talismanValueHud.isKeyDown) {
            mc.player.openGui(DivineFavor, ConstGuiIDs.TALISMAN_HUD, mc.world, 0, 0, 0)
        }
    }
    //
    //    @SubscribeEvent
    //    public static void onMouseInput(@SuppressWarnings("unused") InputEvent.MouseInputEvent event) {
    //        handleEventInput();
    //    }
    //
    //    private static void handleEventInput() {
    //
    //    }
}