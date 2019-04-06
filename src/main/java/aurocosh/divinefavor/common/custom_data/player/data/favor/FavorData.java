package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.custom_data.CapabilityHelper;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder;
import aurocosh.divinefavor.common.misc.SlotStack;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class FavorData {
    public final static int CONTRACT_SLOT_COUNT = 22;

    private final int[] favorValues;
    private final FavorStatus[] favorStatuses;
    private final ItemStackHandler[] contractsStackHandlers;

    public FavorData() {
        List<ModFavor> favors = ModMappers.favors.getValues();
        favorValues = new int[favors.size()];

        favorStatuses = new FavorStatus[favors.size()];
        contractsStackHandlers = new ItemStackHandler[favors.size()];
        for (int i = 0; i < favors.size(); i++) {
            ModFavor favor = favors.get(i);
            favorStatuses[i] = new FavorStatus(favor);
            contractsStackHandlers[i] = new ItemStackHandler(CONTRACT_SLOT_COUNT) {
                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    Item item = stack.getItem();
                    return item instanceof ItemContract || item instanceof ItemContractBinder;
                }
            };
        }
    }

    public ItemStackHandler getContractHandler(int favorId) {
        return contractsStackHandlers[favorId];
    }

    public NBTTagCompound serializeContracts() {
        NBTTagCompound contractsCompound = new NBTTagCompound();
        for (int i = 0; i < contractsStackHandlers.length; i++)
            contractsCompound.setTag(String.valueOf(i), contractsStackHandlers[i].serializeNBT());
        return contractsCompound;
    }

    public void deserializeContracts(NBTTagCompound compound) {
        for (int i = 0; i < contractsStackHandlers.length; i++)
            contractsStackHandlers[i].deserializeNBT(compound.getCompoundTag(String.valueOf(i)));
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
        if (regen == 0)
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

    private List<ItemContract> getContracts(ItemStackHandler contractsStackHandler) {
        List<ItemStack> contractStacks = new ArrayList<>();
        for (int i = 0; i < contractsStackHandler.getSlots(); i++) {
            ItemStack stack = contractsStackHandler.getStackInSlot(i);
            if (!stack.isEmpty())
                contractStacks.addAll(getContracsFromStack(stack));
        }

        List<ItemContract> contracts = new ArrayList<>();
        for (ItemStack stack : contractStacks)
            contracts.add((ItemContract) stack.getItem());
        return contracts;
    }

    private List<ItemStack> getContracsFromStack(ItemStack stack) {
        List<ItemStack> contracts = new ArrayList<>();
        if (stack.getItem() instanceof ItemContract) {
            contracts.add(stack);
        }
        else if (stack.getItem() instanceof ItemContractBinder) {
            IItemHandler handler = CapabilityHelper.getItemHandler(stack);
            if (handler == null)
                return contracts;
            for (SlotStack slotStack : UtilHandler.getNotEmptyStacksWithSlotIndexes(handler))
                contracts.add(slotStack.getStack());
        }
        return contracts;
    }

    public void refreshContracts() {
        for (FavorStatus favorStatus : favorStatuses)
            favorStatus.reset();

        for (int i = 0; i < contractsStackHandlers.length; i++) {
            ItemStackHandler stackHandler = contractsStackHandlers[i];
            List<ItemContract> contracts = getContracts(stackHandler);
            for (ItemContract contract : contracts) {
                FavorStatus status = favorStatuses[i];
                status.addStats(contract);
            }
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
