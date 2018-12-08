package aurocosh.divinefavor.common.block.tile;

import aurocosh.divinefavor.common.block.MediumState;
import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.item.calling_stone.ItemCallingStone;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.ModMultiBlockInstance;
import aurocosh.divinefavor.common.muliblock.MultiBlockWatcher;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.spirit.ModSpirit;
import aurocosh.divinefavor.common.util.UtilHandler;
import aurocosh.divinefavor.common.util.helper_classes.SlotStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileIronMedium extends TickableTileEntity implements IMultiblockController {
    public static final int SIZE = 27;
    private final String TAG_CALLING_STONE = "CallingStone";
    private final String TAG_ITEMS = "Items";
    private MediumState state = MediumState.NO_CALLING_STONE;

    // server side
    private ModMultiBlockInstance multiBlockInstance;
    // client side
    private boolean multiBlockValid;

    private ItemStackHandler stoneStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemCallingStone;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileIronMedium.this.markDirty();
            if(multiBlockInstance != null)
                multiblockDamaged();
            tryToFormMultiBlock();
        }
    };

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
        multiBlockValid = false;
    }

    @Override
    public void onLoad() {
        tryToFormMultiBlockInternal();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(TAG_CALLING_STONE)) {
            stoneStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_CALLING_STONE));
        }
        if (compound.hasKey(TAG_ITEMS)) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_ITEMS));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag(TAG_CALLING_STONE, stoneStackHandler.serializeNBT());
        compound.setTag(TAG_ITEMS, itemStackHandler.serializeNBT());
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
            if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(stoneStackHandler);
            else
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger("state", state.ordinal());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger("state");

        if (world.isRemote && stateIndex != state.ordinal()) {
            state = MediumState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public MediumState getState() {
        return state;
    }

    public void setState(MediumState state) {
        if (this.state != state) {
            this.state = state;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    @Override
    protected void updateFiltered() {
        ItemStack stack = stoneStackHandler.getStackInSlot(0);
        if (stack == ItemStack.EMPTY) {
            setState(MediumState.NO_CALLING_STONE);
            return;
        }

        if (multiBlockInstance == null) {
            setState(MediumState.NO_MULTI_BLOCK);
            return;
        }

        ItemCallingStone item = (ItemCallingStone) stack.getItem();
        ModSpirit spirit = item.spirit;
        if (!spirit.isActive(world)) {
            setState(MediumState.VALID);
            return;
        }
        setState(MediumState.ACTIVE);

        List<SlotStack> slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(itemStackHandler);
        for (SlotStack slotStack : slotStacks) {
            if (slotStack.getStack().getItem() == ModItems.ritual_pouch)
                exchangeRitualPouch(slotStack);
        }
    }

    private void exchangeRitualPouch(SlotStack slotStack) {
        ItemStack stack = slotStack.getStack();
        IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (handler == null)
            return;

        ItemStack stoneStack = stoneStackHandler.getStackInSlot(0);
        if (stoneStack == ItemStack.EMPTY)
            return;

        List<ItemStack> pouchStacks = UtilHandler.getNotEmptyStacks(handler);
        ItemStack result = ModRecipes.getRecipeResult(stoneStack.getItem(), pouchStacks);
        if (result == ItemStack.EMPTY)
            return;
        int slot = slotStack.getIndex();
        itemStackHandler.extractItem(slot, 1, false);
        itemStackHandler.insertItem(slot, result, false);
    }

    @Override
    public ModMultiBlockInstance getMultiblockInstance() {
        return multiBlockInstance;
    }

    @Override
    public void multiblockDamaged() {
        multiBlockInstance = null;
        MultiBlockWatcher.unRegisterController(this);
        setState(MediumState.NO_MULTI_BLOCK);
    }

    @Override
    public void tryToFormMultiBlock() {
        tryToFormMultiBlockInternal();
        updateState();
    }

    private void tryToFormMultiBlockInternal() {
        if (world.isRemote)
            return;
        if (multiBlockInstance != null)
            return;
        ItemStack stack = stoneStackHandler.getStackInSlot(0);
        if (stack == ItemStack.EMPTY)
            return;

        ItemCallingStone callingStone = (ItemCallingStone) stack.getItem();
        ModMultiBlock multiBlock = callingStone.multiBlock;
        Vector3i position = Vector3i.convert(pos);
        multiBlockInstance = multiBlock.makeMultiBlock(world, position);
        if (multiBlockInstance != null)
            MultiBlockWatcher.registerController(this);
    }

    private void updateState(){
        ItemStack stack = stoneStackHandler.getStackInSlot(0);
        if (stack == ItemStack.EMPTY) {
            setState(MediumState.NO_CALLING_STONE);
            return;
        }
        if (multiBlockInstance == null) {
            setState(MediumState.NO_MULTI_BLOCK);
            return;
        }

        ItemCallingStone item = (ItemCallingStone) stack.getItem();
        ModSpirit spirit = item.spirit;
        if (!spirit.isActive(world)) {
            setState(MediumState.VALID);
            return;
        }
        setState(MediumState.ACTIVE);
    }
}