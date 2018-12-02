package aurocosh.divinefavor.common.block.tile;

import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.util.UtilHandler;
import aurocosh.divinefavor.common.util.helper_classes.SlotStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import vazkii.arl.recipe.ModRecipe;

import java.util.ArrayList;
import java.util.List;

public class TileIronMedium extends TickableTileEntity {
    public static final int SIZE = 27;

    // This item handler will hold our nine inventory slots
    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileIronMedium.this.markDirty();
        }
    };

    public TileIronMedium() {
        super(false, true);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    protected void updateFiltered() {
        List<SlotStack> slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(itemStackHandler);
        for (SlotStack slotStack : slotStacks) {
            if(slotStack.getStack().getItem() == ModItems.ritual_pouch)
                exchangeRitualPouch(slotStack);
        }
    }

    private void exchangeRitualPouch(SlotStack slotStack){
        ItemStack stack = slotStack.getStack();
        IItemHandler handler = stack.getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null );
        if(handler == null)
            return;

        List<ItemStack> pouchStacks = UtilHandler.getNotEmptyStacks(handler);
        ItemStack result = ModRecipes.getRecipeResult(pouchStacks);
        if(result == ItemStack.EMPTY)
            return;
        int slot = slotStack.getIndex();
        itemStackHandler.extractItem(slot,1,false);
        itemStackHandler.insertItem(slot,result,false);
    }
}