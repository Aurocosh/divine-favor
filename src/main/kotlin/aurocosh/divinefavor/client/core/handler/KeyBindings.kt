package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.settings.IKeyConflictContext
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.input.Keyboard

@SideOnly(Side.CLIENT)
object KeyBindings {
    private val CONFLICT_CONTEXT = KeyConflictContext()
    lateinit var talismanScroll: KeyBinding
    lateinit var talismanSelect: KeyBinding
//    lateinit var ropeKey: KeyBinding

    fun init() {
        talismanScroll = createBinding("talisman_scroll", Keyboard.KEY_R)
        talismanSelect = createBinding("talisman_select", Keyboard.KEY_V)
//        ropeKey = createBinding("rope_key", Keyboard.KEY_N)
    }

    private fun createBinding(name: String, key: Int): KeyBinding {
        val keyBinding = KeyBinding("key." + ResourceNamer.getFullName(name), CONFLICT_CONTEXT, key, DivineFavor.KEYBIND_CATEGORY)
        ClientRegistry.registerKeyBinding(keyBinding)
        return keyBinding
    }

    class KeyConflictContext : IKeyConflictContext {
        override fun isActive(): Boolean {
            val player = DivineFavor.proxy.clientPlayer
//            if (!net.minecraftforge.client.settings.KeyConflictContext.GUI.isActive())
            //                return false;
            return UtilPlayer.getHandWithItem(player) { TalismanContainerAdapter.isItemValid(it) } != null
        }

        override fun conflicts(other: IKeyConflictContext): Boolean {
            return false
            //            return other == this;
            //            return other == this || other == net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
        }
    }
}