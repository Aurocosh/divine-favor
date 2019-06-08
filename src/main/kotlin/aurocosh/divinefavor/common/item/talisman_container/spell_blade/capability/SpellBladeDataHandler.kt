package aurocosh.divinefavor.common.item.talisman_container.spell_blade.capability

import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.SpellBladeStorage
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object SpellBladeDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ISpellBladeHandler::class)
    private val CAPABILITY: Capability<ISpellBladeHandler>? = null

    val CAPABILITY_SPELL_BLADE: Capability<ISpellBladeHandler>
        get() = CAPABILITY as Capability<ISpellBladeHandler>

    // Handles all of the required registration for the capability.
    fun register() {
        CapabilityManager.INSTANCE.register(ISpellBladeHandler::class.java, SpellBladeStorage(), DefaultSpellBladeHandler::class.java)
        MinecraftForge.EVENT_BUS.register(this)
    }

    //    // Allows the provider to be attached to a target entity.
    //    @SubscribeEvent
    //    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
    //        ItemStack object = event.getObject();
    //        assert object != null;
    //        if(object.getItem() == ModItems.grimoire)
    //            event.addCapability(ResourceNamer.getFullName("grimoire_capability"), new SpellBowProvider());
    //    }
}