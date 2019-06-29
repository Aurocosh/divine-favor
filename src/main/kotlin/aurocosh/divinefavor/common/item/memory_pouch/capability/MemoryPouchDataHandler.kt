package aurocosh.divinefavor.common.item.memory_pouch.capability

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object MemoryPouchDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IMemoryPouch::class)
    private val capability: Capability<IMemoryPouch>? = null

    val CAPABILITY_MEMORY_POUCH get() = capability as Capability<IMemoryPouch>

    // Handles all of the required registration for the capability.
    fun register() {
        CapabilityManager.INSTANCE.register(IMemoryPouch::class.java, MemoryPouchStorage(), DefaultMemoryPouchHandler::class.java)
        MinecraftForge.EVENT_BUS.register(this)
    }
}