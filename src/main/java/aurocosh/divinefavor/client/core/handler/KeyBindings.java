package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
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
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player == null)
                return false;
//            if (!net.minecraftforge.client.settings.KeyConflictContext.GUI.isActive())
//                return false;
            EnumHand hand = UtilPlayer.getHand(element -> {
                Item item = player.getHeldItem(element).getItem();
                return item instanceof ItemGrimoire || item instanceof ItemSpellBow;
            });
            return hand != null;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return other == this;
//            return other == this || other == net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
        }
    }
}