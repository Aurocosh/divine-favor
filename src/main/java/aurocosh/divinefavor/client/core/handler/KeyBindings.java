package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings {
    private static final KeyConflictContext CONFLICT_CONTEXT = new KeyConflictContext();
    public static KeyBinding talismanScroll;
    public static KeyBinding talismanSelect;

    public static void init() {
        talismanScroll = createBinding("talisman_scroll", Keyboard.KEY_R);
        talismanSelect = createBinding("talisman_select", Keyboard.KEY_V);
    }

    private static KeyBinding createBinding(String name, int key) {
        KeyBinding keyBinding = new KeyBinding("key." + ResourceNamer.getFullName(name), CONFLICT_CONTEXT, key, ConstMisc.KEYBIND_CATEGORY);
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

    public static class KeyConflictContext implements IKeyConflictContext {
        @Override
        public boolean isActive() {
            EntityPlayer player = DivineFavor.proxy.getClientPlayer();
            if (player == null)
                return false;
//            if (!net.minecraftforge.client.settings.KeyConflictContext.GUI.isActive())
//                return false;
            return UtilPlayer.getHandWithItem(player, TalismanContainerAdapter::isItemValid) != null;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return false;
//            return other == this;
//            return other == this || other == net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
        }
    }
}