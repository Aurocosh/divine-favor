package aurocosh.divinefavor.common.custom_data.player.data.contracts;

import aurocosh.divinefavor.common.item.contract.ItemContract;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

// The default implementation of the capability. Holds all the logic.
public class ContractsData {
    private final Set<ItemContract> contracts;
    private final ItemStackHandler contractsStackHandler;

    public ContractsData() {
        contracts = new HashSet<>();
        contractsStackHandler = new ItemStackHandler(22) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof ItemContract;
            }

            @Override
            protected void onContentsChanged(int slot) {
                refreshContracts();
            }
        };
    }

    private void refreshContracts() {
        contracts.clear();
        for (int i = 0; i < contractsStackHandler.getSlots(); i++) {
            ItemStack stack = contractsStackHandler.getStackInSlot(i);
            if (!stack.isEmpty())
                contracts.add((ItemContract) stack.getItem());
        }
    }

    public boolean hasContract(ItemContract contract) {
        return contracts.contains(contract);
    }

    public ItemStackHandler getStackHandler() {
        return contractsStackHandler;
    }

}
