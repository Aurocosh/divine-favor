package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder;
import aurocosh.divinefavor.common.misc.SlotStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class UtilContract {
    public static List<ItemStack> getContracsFromStack(ItemStack stack) {
        List<ItemStack> contracts = new ArrayList<>();
        if (stack.getItem() instanceof ItemContract) {
            contracts.add(stack);
        }
        else if (stack.getItem() instanceof ItemContractBinder) {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (handler == null)
                return contracts;
            for (SlotStack slotStack : UtilHandler.getNotEmptyStacksWithSlotIndexes(handler))
                contracts.add(slotStack.getStack());
        }
        return contracts;
    }

    public static List<UUID> getPlayerUUIDs(List<ItemStack> contractStacks, ItemCallingStone callingStone) {
        Set<UUID> playerUUIDs = new HashSet<>();
        for (ItemStack stack : contractStacks) {
            ItemContract contract = (ItemContract) stack.getItem();
            if (contract.getSpirit() != callingStone.spirit)
                continue;
            UUID uuid = ItemContract.getPlayerId(stack);
            if (uuid != null)
                playerUUIDs.add(uuid);
        }
        return new ArrayList<>(playerUUIDs);
    }
}
