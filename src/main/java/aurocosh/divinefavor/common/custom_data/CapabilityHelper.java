package aurocosh.divinefavor.common.custom_data;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CapabilityHelper {
    public static IItemHandler getItemHandler(ICapabilityProvider provider) {
        return getItemHandler(provider, null);
    }

    public static IItemHandler getItemHandler(ICapabilityProvider provider, EnumFacing facing) {
        return provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
    }
}
