package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talisman.ITalismanToolContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.settings.IKeyConflictContext
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.input.Keyboard

@SideOnly(Side.CLIENT)
object KeyBindings {
    private val CONTAINER_CONFLICT_CONTEXT = ContainerKeyConflictContext()
    private val TALISMAN_CONFLICT_CONTEXT = TalismanKeyConflictContext()

    lateinit var talismanScroll: KeyBinding
    lateinit var talismanSelect: KeyBinding
    lateinit var talismanValueHud: KeyBinding
//    lateinit var ropeKey: KeyBinding

    fun init() {
        talismanScroll = createBinding("talisman_scroll", Keyboard.KEY_Y, CONTAINER_CONFLICT_CONTEXT)
        talismanSelect = createBinding("talisman_select", Keyboard.KEY_V, CONTAINER_CONFLICT_CONTEXT)
        talismanValueHud = createBinding("talisman_value_hud", Keyboard.KEY_R, TALISMAN_CONFLICT_CONTEXT)
//        ropeKey = createBinding("rope_key", Keyboard.KEY_N)
    }

    private fun createBinding(name: String, key: Int, conflictContext: IKeyConflictContext): KeyBinding {
        val keyBinding = KeyBinding("key.${DivineFavor.MOD_ID}.$name", conflictContext, key, DivineFavor.KEYBIND_CATEGORY)
        ClientRegistry.registerKeyBinding(keyBinding)
        return keyBinding
    }

    class ContainerKeyConflictContext : IKeyConflictContext {
        override fun isActive(): Boolean {
            if (!DivineFavor.proxy.hasClientPlayer)
                return false
            return UtilPlayer.getHandWithItem(DivineFavor.proxy.clientPlayer) { it is ITalismanToolContainer } != null
        }

        override fun conflicts(other: IKeyConflictContext): Boolean {
            return other == this || other == TALISMAN_CONFLICT_CONTEXT;
        }
    }

    class TalismanKeyConflictContext : IKeyConflictContext {
        override fun isActive(): Boolean {
            if (!DivineFavor.proxy.hasClientPlayer)
                return false
            return UtilPlayer.getHandWithItem(DivineFavor.proxy.clientPlayer) { it is ItemTalisman || it is ITalismanToolContainer } != null
        }

        override fun conflicts(other: IKeyConflictContext): Boolean {
            return other == this || other == CONTAINER_CONFLICT_CONTEXT
        }
    }
}