package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.client.core.handler.grimoire.GrimoireSelectGui;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class EventInputHandler {
    @SubscribeEvent
    public static void onKeyInput(@SuppressWarnings("unused") InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (KeyBindings.talismanSelect.isPressed()) {
            if (UtilPlayer.getHandWithItem(mc.player, item -> item instanceof ItemGrimoire) != null)
                mc.displayGuiScreen(GrimoireSelectGui.INSTANCE);
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