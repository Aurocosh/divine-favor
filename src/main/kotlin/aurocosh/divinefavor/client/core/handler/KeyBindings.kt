package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.item.talisman_tools.TalismanAdapter
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
    private val TEST_CONFLICT_CONTEXT = TestKeyConflictContext()
    lateinit var talismanScroll: KeyBinding
    lateinit var talismanSelect: KeyBinding
    lateinit var talismanValueHud: KeyBinding
//    lateinit var ropeKey: KeyBinding

    fun init() {
        talismanScroll = createBinding("talisman_scroll", Keyboard.KEY_Y, CONFLICT_CONTEXT)
        talismanSelect = createBinding("talisman_select", Keyboard.KEY_V, CONFLICT_CONTEXT)
        talismanValueHud = createBinding("talisman_value_hud", Keyboard.KEY_R, TEST_CONFLICT_CONTEXT)
//        ropeKey = createBinding("rope_key", Keyboard.KEY_N)
    }

    private fun createBinding(name: String, key: Int, conflictContext: IKeyConflictContext): KeyBinding {
        val keyBinding = KeyBinding("key." + ResourceNamer.getFullName(name), conflictContext, key, DivineFavor.KEYBIND_CATEGORY)
        ClientRegistry.registerKeyBinding(keyBinding)
        return keyBinding
    }

    class KeyConflictContext : IKeyConflictContext {
        override fun isActive(): Boolean {
            val player = DivineFavor.proxy.clientPlayer
//            if (!net.minecraftforge.client.settings.KeyConflictContext.GUI.isActive())
            //                return false;
            return UtilPlayer.getHandWithItem(player) { TalismanAdapter.isItemValid(it) } != null
        }

        override fun conflicts(other: IKeyConflictContext): Boolean {
            return false
            //            return other == this;
            //            return other == this || other == net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
        }
    }

    class TestKeyConflictContext : IKeyConflictContext {
        override fun isActive(): Boolean {
            val player = DivineFavor.proxy.clientPlayer
//            if (!net.minecraftforge.client.settings.KeyConflictContext.GUI.isActive())
            //                return false;
            return UtilPlayer.getHandWithItem(player) { it is ItemTalisman || TalismanAdapter.isItemValid(it) } != null
        }

        override fun conflicts(other: IKeyConflictContext): Boolean {
            return false
            //            return other == this;
            //            return other == this || other == net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
        }
    }
}