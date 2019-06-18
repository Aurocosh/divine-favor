package aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability

import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.ISpellBladeHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object SpellPickDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ISpellPickHandler::class)
    private val CAPABILITY: Capability<ISpellPickHandler>? = null

    val CAPABILITY_SPELL_PICK: Capability<ISpellPickHandler>
        get() = CAPABILITY as Capability<ISpellPickHandler>

    // Handles all of the required registration for the capability.
    fun register() {
        CapabilityManager.INSTANCE.register(ISpellPickHandler::class.java, SpellPickStorage(), DefaultSpellPickHandler::class.java)
        MinecraftForge.EVENT_BUS.register(this)
    }
}