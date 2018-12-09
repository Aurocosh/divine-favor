package aurocosh.divinefavor.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ClientTickHandler {
    @SubscribeEvent
    public void clientTickEnd(ClientTickEvent event) {
        if (event.phase != Phase.END)
            return;
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui == null || !gui.doesGuiPauseGame()) {
            if (gui == null && KeybindHandler.keybind.isKeyDown())
                KeybindHandler.keyDown();
        }
    }
}