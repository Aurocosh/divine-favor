package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.core.ResourceNamer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class LivingDataDataHandler {
    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    fun attachCapabilities(event: AttachCapabilitiesEvent<Entity>) {
        if (event.getObject() is EntityLivingBase)
            event.addCapability(ResourceNamer.getFullName("capability_living_data"), LivingDataProvider())
    }

    companion object {
        // The Capability field. Used for checks and references.
        // Initialized when forge registers the capability.
        @CapabilityInject(ILivingDataHandler::class)
        val CAPABILITY_LIVING_DATA: Capability<ILivingDataHandler>? = null

        // Handles all of the required registration for the capability.
        fun register() {
            CapabilityManager.INSTANCE.register(ILivingDataHandler::class.java, LivingDataStorage(), DefaultLivingDataHandler::class.java)
            MinecraftForge.EVENT_BUS.register(LivingDataDataHandler())
        }
    }
}