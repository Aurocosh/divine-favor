package aurocosh.divinefavor.client.core.handler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeybindHandler {

    public static KeyBinding keybind;

    public static void init() {
        keybind = new KeyBinding("psimisc.keybind", Keyboard.KEY_C, "key.categories.inventory");
        ClientRegistry.registerKeyBinding(keybind);
    }

    public static void keyDown() {
        /*
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
        if(!stack.isEmpty() && (stack.getItem() instanceof ISocketable || stack.getItem() instanceof ISocketableController))
            mc.displayGuiScreen(new GuiSocketSelect(stack));
        else {
            stack = mc.player.getHeldItem(EnumHand.OFF_HAND);
            if(!stack.isEmpty() && (stack.getItem() instanceof ISocketable || stack.getItem() instanceof ISocketableController))
                mc.displayGuiScreen(new GuiSocketSelect(stack));
            else mc.displayGuiScreen(new GuiLeveling());
        }*/
    }
}