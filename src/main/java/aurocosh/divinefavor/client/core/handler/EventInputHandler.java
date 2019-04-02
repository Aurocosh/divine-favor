package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.client.core.handler.talisman_container.TalismanSelectGui;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class EventInputHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(@SuppressWarnings("unused") InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if (KeyBindings.talismanSelect.isKeyDown()) {
            if (UtilPlayer.getHandWithItem(mc.player, TalismanContainerAdapter::isItemValid) != null)
                mc.displayGuiScreen(TalismanSelectGui.INSTANCE);
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