package aurocosh.divinefavor.common.custom_data.world.capability

import aurocosh.divinefavor.common.core.ResourceNamer
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class WorldDataDataHandler {
    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    fun attachCapabilities(event: AttachCapabilitiesEvent<World>) {
        event.addCapability(ResourceNamer.getFullName("capability_world_data_divine"), WorldDataProvider())
    }

    companion object {
        // The Capability field. Used for checks and references.
        // Initialized when forge registers the capability.
        @CapabilityInject(IWorldDataHandler::class)
        val capability: Capability<IWorldDataHandler>? = null

        val CAPABILITY_WORLD_DATA_DIVINE: Capability<IWorldDataHandler>
            get() = capability as Capability<IWorldDataHandler>

        // Handles all of the required registration for the capability.
        fun register() {
            CapabilityManager.INSTANCE.register(IWorldDataHandler::class.java, WorldDataStorage(), DefaultWorldDataHandler::class.java)
            MinecraftForge.EVENT_BUS.register(WorldDataDataHandler())
        }
    }
}