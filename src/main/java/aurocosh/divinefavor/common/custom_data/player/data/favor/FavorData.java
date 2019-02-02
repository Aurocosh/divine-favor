package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class FavorData {
    public final static int CONTRACT_SLOT_COUNT = 22;

    private final int[] favorValues;
    private final FavorStatus[] favorStatuses;
    private final ItemStackHandler contractsStackHandler;

    public FavorData() {
        List<ModFavor> favors = ModMappers.favors.getValues();
        favorValues = new int[favors.size()];

        favorStatuses = new FavorStatus[favors.size()];
        for (int i = 0; i < favors.size(); i++)
            favorStatuses[i] = new FavorStatus();

        contractsStackHandler = new ItemStackHandler(CONTRACT_SLOT_COUNT) {
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

    public ItemStackHandler getContractHandler() {
        return contractsStackHandler;
    }

    public NBTTagCompound serializeContract() {
        return contractsStackHandler.serializeNBT();
    }

    public void deserializeContract(NBTTagCompound compound) {
        contractsStackHandler.deserializeNBT(compound);
        refreshContracts();
    }

    public int getFavor(int favorId) {
        return favorValues[favorId];
    }

    public void setFavor(int favorId, int value) {
        favorValues[favorId] = favorStatuses[favorId].clamp(value);
    }

    public void addFavor(int favorId, int value) {
        favorValues[favorId] = favorStatuses[favorId].clamp(favorValues[favorId] + value);
    }

    public boolean consumeFavor(int favorId, int value) {
        int favorValue = favorValues[favorId];
        if (favorValue < value)
            return false;
        favorValues[favorId] = favorStatuses[favorId].clamp(favorValues[favorId] - value);
        return true;
    }

    public int getMaxFavor(int favorId) {
        return favorStatuses[favorId].getMaxLimit();
    }

    public boolean regenerateFavor(int favorId) {
        FavorStatus status = favorStatuses[favorId];
        int regen = status.getRegen();
        if(regen == 0)
            return false;
        favorValues[favorId] = status.clamp(favorValues[favorId] + regen);
        return true;
    }

    public int[] getFavorValues() {
        return favorValues;
    }

    public void setFavorValues(int[] favorValues) {
        System.arraycopy(favorValues, 0, favorValues, 0, favorValues.length);
        refreshValues();
    }

    private List<ItemContract> getContracts() {
        List<ItemContract> contracts = new ArrayList<>();
        for (int i = 0; i < contractsStackHandler.getSlots(); i++) {
            ItemStack stack = contractsStackHandler.getStackInSlot(i);
            if (!stack.isEmpty())
                contracts.add((ItemContract) stack.getItem());
        }
        return contracts;
    }

    private void refreshContracts() {
        for (FavorStatus favorStatus : favorStatuses)
            favorStatus.clear();

        List<ItemContract> contracts = getContracts();
        for (ItemContract contract : contracts) {
            int favorId = contract.getFavor().getId();
            FavorStatus status = favorStatuses[favorId];
            status.addStats(contract);
        }
        refreshValues();
    }

    private void refreshValues() {
        for (int i = 0; i < favorValues.length; i++) {
            FavorStatus status = favorStatuses[i];
            favorValues[i] = status.clamp(favorValues[i]);
        }
    }
}
