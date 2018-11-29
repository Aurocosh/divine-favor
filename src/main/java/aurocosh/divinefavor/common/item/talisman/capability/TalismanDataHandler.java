package aurocosh.divinefavor.common.item.talisman.capability;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TalismanDataHandler {
    public static ITalismanCostHandler getHandler(ItemStack stack) {
        if (stack.hasCapability(CAPABILITY_TALISMAN_COST, EnumFacing.DOWN))
            return stack.getCapability(CAPABILITY_TALISMAN_COST, EnumFacing.DOWN);
        return null;
    }

    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(ITalismanCostHandler.class)
    public static final Capability<ITalismanCostHandler> CAPABILITY_TALISMAN_COST = null;

    // Handles all of the required registration for the capability.
    public static void register() {
        CapabilityManager.INSTANCE.register(ITalismanCostHandler.class, new TalismanCostStorage(), DefaultTalismanCostHandler.class);
        MinecraftForge.EVENT_BUS.register(new TalismanDataHandler());
    }

    // Allows the provider to be attached to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ItemTalisman)
            event.addCapability(new ResourceLocation(LibMisc.MOD_ID, "talisman_cost_capability"), new Provider());
    }

    // The default implementation of the capability. Holds all the logic.
    public static class DefaultTalismanCostHandler implements ITalismanCostHandler {
        private int unitIndex = 0;
        private int costIndex = 0;

        @Override
        public int getSelectedUnitIndex() {
            return unitIndex;
        }

        @Override
        public int setSelectedUnitIndex(int index) {
            return unitIndex = index;
        }

        @Override
        public int getSelectedCostIndex() {
            return costIndex;
        }

        @Override
        public int setSelectedCostIndex(int index) {
            return costIndex = index;
        }
    }

}