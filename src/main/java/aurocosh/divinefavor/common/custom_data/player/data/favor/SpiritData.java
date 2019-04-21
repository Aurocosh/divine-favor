package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.custom_data.CapabilityHelper;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.item.contract.ItemFavorContract;
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder;
import aurocosh.divinefavor.common.misc.SlotStack;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilHandler;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class SpiritData {
    public final static int CONTRACT_SLOT_COUNT = 22;

    private final int[] favorValues;
    private final SpiritStatus[] spiritStatuses;
    private final ItemStackHandler[] contractsStackHandlers;

    public SpiritData() {
        List<ModSpirit> spirits = ModMappers.spirits.getValues();
        favorValues = new int[spirits.size()];

        spiritStatuses = new SpiritStatus[spirits.size()];
        contractsStackHandlers = new ItemStackHandler[spirits.size()];
        for (int i = 0; i < spirits.size(); i++) {
            ModSpirit favor = spirits.get(i);
            spiritStatuses[i] = new SpiritStatus(favor);
            contractsStackHandlers[i] = new ItemStackHandler(CONTRACT_SLOT_COUNT) {
                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    Item item = stack.getItem();
                    return item instanceof ItemContract || item instanceof ItemContractBinder;
                }
            };
        }
    }

    public ItemStackHandler getContractHandler(int spiritId) {
        return contractsStackHandlers[spiritId];
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

    public boolean isInform(int spiritId) {
        return spiritStatuses[spiritId].isInformActivity();
    }

    public int getFavor(int spiritId) {
        return favorValues[spiritId];
    }

    public void setFavor(int spiritId, int value) {
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(value);
    }

    public void addFavor(int spiritId, int value) {
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(favorValues[spiritId] + value);
    }

    public boolean consumeFavor(int spiritId, int value) {
        int favorValue = favorValues[spiritId];
        if (favorValue < value)
            return false;
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(favorValues[spiritId] - value);
        return true;
    }

    public int getMaxFavor(int spiritId) {
        return spiritStatuses[spiritId].getMaxLimit();
    }

    public boolean regenerateFavor(int spiritId) {
        SpiritStatus status = spiritStatuses[spiritId];
        int regen = status.getRegen();
        if (regen == 0)
            return false;
        favorValues[spiritId] = status.clamp(favorValues[spiritId] + regen);
        return true;
    }

    public int[] getFavorValues() {
        return favorValues;
    }

    public void setFavorValues(int[] values) {
        System.arraycopy(values, 0, favorValues, 0, values.length);
        refreshValues();
    }

    private List<ItemContract> getContracts(ItemStackHandler contractsStackHandler) {
        List<ItemStack> contractStacks = new ArrayList<>();
        for (int i = 0; i < contractsStackHandler.getSlots(); i++) {
            ItemStack stack = contractsStackHandler.getStackInSlot(i);
            if (!stack.isEmpty())
                contractStacks.addAll(getContractsFromStack(stack));
        }

        List<ItemContract> contracts = new ArrayList<>();
        for (ItemStack stack : contractStacks) {
            Item item = stack.getItem();
                contracts.add((ItemContract) item);
        }
        return contracts;
    }

    private List<ItemStack> getContractsFromStack(ItemStack stack) {
        List<ItemStack> contracts = new ArrayList<>();
        if (stack.getItem() instanceof ItemContract)
            contracts.add(stack);
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
        for (SpiritStatus spiritStatus : spiritStatuses)
            spiritStatus.reset();

        for (int i = 0; i < contractsStackHandlers.length; i++) {
            ItemStackHandler stackHandler = contractsStackHandlers[i];
            List<ItemContract> contracts = getContracts(stackHandler);

            List<ItemFavorContract> favorContracts = UtilList.select(contracts, ItemFavorContract.class);
            for (ItemFavorContract contract : favorContracts)
                spiritStatuses[i].addStats(contract);
        }
        refreshValues();
    }

    private void refreshValues() {
        for (int i = 0; i < favorValues.length; i++) {
            SpiritStatus status = spiritStatuses[i];
            favorValues[i] = status.clamp(favorValues[i]);
        }
    }
}
