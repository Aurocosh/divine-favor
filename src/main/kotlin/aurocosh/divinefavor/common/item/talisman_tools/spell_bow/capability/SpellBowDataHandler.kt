package aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability

import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object SpellBowDataHandler {
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ISpellBowHandler::class)
    val capability: Capability<ISpellBowHandler>? = null

    val CAPABILITY_SPELL_BOW: Capability<ISpellBowHandler>
        get() = capability as Capability<ISpellBowHandler>

    // Handles all of the required registration for the capability.
    fun register() {
        CapabilityManager.INSTANCE.register(ISpellBowHandler::class.java, SpellBowStorage(), DefaultSpellBowHandler::class.java)
        MinecraftForge.EVENT_BUS.register(this)
    }

    fun get(stack: ItemStack): ISpellBowHandler? {
        return stack.getCapability(CAPABILITY_SPELL_BOW, null)
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