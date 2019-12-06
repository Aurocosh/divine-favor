package aurocosh.divinefavor.common.item.gem_pouch.capability

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object GemPouchDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IGemPouchHandler::class)
    private val capability: Capability<IGemPouchHandler>? = null

    val CAPABILITY_GEM_POUCH: Capability<IGemPouchHandler>
        get() = capability as Capability<IGemPouchHandler>

    // Handles all of the required registration for the capability.
    fun register() {
        CapabilityManager.INSTANCE.register(IGemPouchHandler::class.java, GemPouchStorage(), DefaultGemPouchHandler::class.java)
        MinecraftForge.EVENT_BUS.register(this)
    }
}