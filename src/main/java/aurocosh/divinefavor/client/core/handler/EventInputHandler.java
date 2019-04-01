package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.client.core.handler.grimoire.GrimoireSelectGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(Side.CLIENT)
public class EventInputHandler {
    public static GrimoireSelectGui talismanSelector = new GrimoireSelectGui();

    @SubscribeEvent
    public static void onKeyInput(@SuppressWarnings("unused") InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ){
            mc.displayGuiScreen(talismanSelector);
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