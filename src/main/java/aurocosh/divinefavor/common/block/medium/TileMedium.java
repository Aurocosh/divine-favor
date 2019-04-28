package aurocosh.divinefavor.common.block.medium;

import aurocosh.divinefavor.common.block.base.TickableTileEntity;
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.misc.SlotStack;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstanceAltar;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilHandler;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileMedium extends TickableTileEntity implements IMultiblockController {
    public static final int SIZE = 27;
    public static final int TICK_RATE = UtilTick.secondsToTicks(2);
    public static final int OFFERING_ACTIVE_TIME = UtilTick.secondsToTicks(10);
    private final String TAG_CALLING_STONE = "CallingStone";
    private final String TAG_ITEMS_LEFT = "ItemsLeft";
    private final String TAG_ITEMS_RIGHT = "ItemsRight";
    private final String TAG_STATE_MEDIUM = "StateMedium";
    private final String TAG_STONE_MEDIUM = "StoneMedium";
    private final String TAG_EXTRA_ACTIVE_TIME = "ExtraActiveTime";

    private MediumState state = MediumState.INVALID;
    private MediumStone stone = MediumStone.NONE;

    // server side
    private int extraActiveTime;
    private boolean isRejecting;
    private MultiBlockInstanceAltar multiBlockInstance;

    private ItemStackHandler stoneStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemCallingStone && stack.getCount() == 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (!world.isRemote) {
                isRejecting = true;
                if (multiBlockInstance == null)
                    tryToFormMultiBlockInternal();
                updateState();
                IBlockState blockState = world.getBlockState(pos);
                getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
            }
            markDirty();
        }
    };

    private ItemStackHandler leftStackHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    private ItemStackHandler rightStackHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    public TileMedium() {
        super(false, true);
        extraActiveTime = 0;
        isRejecting = false;
        setTickRate(TICK_RATE);
    }

    @Override
    public void onLoad() {
        tryToFormMultiBlockInternal();
    }

    public ItemStackHandler getStoneStackHandler() {
        return stoneStackHandler;
    }

    public ItemStackHandler getLeftStackHandler() {
        return leftStackHandler;
    }

    public ItemStackHandler getRightStackHandler() {
        return rightStackHandler;
    }

    CombinedInvWrapper combinedHandler = new CombinedInvWrapper(leftStackHandler, rightStackHandler);

    public ItemStack getStoneStack() {
        return stoneStackHandler.getStackInSlot(0);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        extraActiveTime = compound.getInteger(TAG_EXTRA_ACTIVE_TIME);
        state = MediumState.VALUES[compound.getInteger(TAG_STATE_MEDIUM)];
        stone = MediumStone.VALUES[compound.getInteger(TAG_STONE_MEDIUM)];
        if (compound.hasKey(TAG_CALLING_STONE))
            stoneStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_CALLING_STONE));
        if (compound.hasKey(TAG_ITEMS_LEFT))
            leftStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_ITEMS_LEFT));
        if (compound.hasKey(TAG_ITEMS_RIGHT))
            rightStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_ITEMS_RIGHT));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_EXTRA_ACTIVE_TIME, extraActiveTime);
        compound.setInteger(TAG_STATE_MEDIUM, state.ordinal());
        compound.setInteger(TAG_STONE_MEDIUM, stone.ordinal());
        compound.setTag(TAG_CALLING_STONE, stoneStackHandler.serializeNBT());
        compound.setTag(TAG_ITEMS_LEFT, leftStackHandler.serializeNBT());
        compound.setTag(TAG_ITEMS_RIGHT, rightStackHandler.serializeNBT());
        return compound;
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot gainFavor it
        if (isRejecting) {
            isRejecting = false;
            return false;
        }
        return state != MediumState.ACTIVE && !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return facing == EnumFacing.WEST || facing == EnumFacing.EAST;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.WEST)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(leftStackHandler);
            else if (facing == EnumFacing.EAST)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(rightStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger(TAG_STATE_MEDIUM, state.ordinal());
        nbtTag.setInteger(TAG_STONE_MEDIUM, stone.ordinal());
        nbtTag.setTag(TAG_CALLING_STONE, stoneStackHandler.serializeNBT());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        if (!world.isRemote)
            return;

        NBTTagCompound compound = packet.getNbtCompound();
        int stateIndex = compound.getInteger(TAG_STATE_MEDIUM);
        if (stateIndex != state.ordinal()) {
            state = MediumState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        int stoneIndex = compound.getInteger(TAG_STONE_MEDIUM);
        if (stoneIndex != stone.ordinal()) {
            stone = MediumStone.VALUES[stoneIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }

        if (compound.hasKey(TAG_CALLING_STONE))
            stoneStackHandler.deserializeNBT(compound.getCompoundTag(TAG_CALLING_STONE));
    }

    public MediumStone getStone() {
        return stone;
    }

    public void setStone(MediumStone stone) {
        if (this.stone == stone)
            return;

        this.stone = stone;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    public MediumState getState() {
        return state;
    }

    public void setState(MediumState state) {
        if (this.state == state)
            return;

        this.state = state;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    @Override
    protected void updateFiltered() {
        ItemStack stoneStack = getStoneStack();
        if (!stoneStack.isEmpty()){
            ItemCallingStone callingStone = (ItemCallingStone) stoneStack.getItem();
            List<SlotStack> slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(combinedHandler);
            checkForOfferings(callingStone, slotStacks);
            processCraftingRecipes(callingStone, slotStacks);
        }

        updateState();

        if (extraActiveTime > 0)
            extraActiveTime -= TICK_RATE;
    }

    private void checkForOfferings(ItemCallingStone callingStone, List<SlotStack> slotStacks) {
        Item offering = callingStone.getSpirit().getOffering();
        int offeringCount = callingStone.getSpirit().getOfferingCount();

        for (SlotStack slotStack : slotStacks) {
            ItemStack stack = slotStack.getStack();
            if (stack.getItem() == offering) {
                if(stack.getCount() >= offeringCount)
                    extraActiveTime = OFFERING_ACTIVE_TIME;
                combinedHandler.setStackInSlot(slotStack.getIndex(), ItemStack.EMPTY);
            }
        }
    }

    private void processCraftingRecipes(ItemCallingStone callingStone, List<SlotStack> slotStacks) {
        if (!callingStone.getSpirit().isActive() && extraActiveTime <= 0)
            return;

        for (SlotStack slotStack : slotStacks) {
            ItemStack stack = slotStack.getStack();
            if (stack.getItem() == ModItems.INSTANCE.getRitual_pouch()) {
                IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (handler != null)
                    exchangeRecipe(callingStone, handler, 3);
            }
        }

        exchangeRecipe(callingStone, leftStackHandler, 4);
        exchangeRecipe(callingStone, rightStackHandler, 4);
    }

    private void exchangeRecipe(ItemCallingStone callingStone, IItemHandler stackHandler, int resultIndex) {
        List<SlotStack> slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(stackHandler);
        List<ItemStack> stacks = new ArrayList<>();
        for (SlotStack slotStack : slotStacks)
            stacks.add(slotStack.getStack());

        ItemStack result = ModRecipes.getRecipeResult(callingStone, stacks);
        if (result == ItemStack.EMPTY)
            return;
        for (SlotStack slotStack : slotStacks)
            stackHandler.extractItem(slotStack.getIndex(), slotStack.getStack().getCount(), false);
        stackHandler.insertItem(resultIndex, result, false);
    }

    @Override
    public MultiBlockInstance getMultiblockInstance() {
        return multiBlockInstance;
    }

    @Override
    public void multiblockDeconstructed() {
        MultiblockWatcher.INSTANCE.unRegisterController(this);
        multiBlockInstance = null;
        updateState();
//        AltarsData altarData = WorldData.get(world).getAltarData();
//        altarData.removeAltarLocation(pos);
    }

    @Override
    public void multiblockDamaged(EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        MultiBlockInstanceAltar instance = multiBlockInstance;
        multiblockDeconstructed();
        if (player != null && instance.getSpirit().isActive())
            instance.getSpirit().getPunishment().execute(player, world, pos, state, instance);
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
        if (stack.isEmpty())
            return;

        ItemCallingStone callingStone = (ItemCallingStone) stack.getItem();
        ModMultiBlock multiBlock = callingStone.getMultiBlock();
        multiBlockInstance = multiBlock.makeMultiBlock(callingStone.getSpirit(), world, pos);
        if (multiBlockInstance != null) {
            MultiblockWatcher.INSTANCE.registerController(this);
//            AltarsData altarData = WorldData.get(world).getAltarData();
//            altarData.addAltarLocation(callingStone.spirit, pos);
        }
    }

    private void updateState() {
        ItemStack stack = getStoneStack();
        if (!stack.isEmpty()) {
            ItemCallingStone callingStone = (ItemCallingStone) stack.getItem();
            if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_arbow())
                setStone(MediumStone.ARBOW);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_blizrabi())
                setStone(MediumStone.BLIZRABI);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_endererer())
                setStone(MediumStone.ENDERERER);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_loon())
                setStone(MediumStone.LOON);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_neblaze())
                setStone(MediumStone.NEBLAZE);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_redwind())
                setStone(MediumStone.REDWIND);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_romol())
                setStone(MediumStone.ROMOL);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_squarefury())
                setStone(MediumStone.SQUAREFURY);
            else if (callingStone == ModCallingStones.INSTANCE.getCalling_stone_timber())
                setStone(MediumStone.TIMBER);

            if (multiBlockInstance != null) {
                ModSpirit spirit = callingStone.getSpirit();
                if (spirit.isActive() || extraActiveTime > 0)
                    setState(MediumState.ACTIVE);
                else
                    setState(MediumState.VALID);
            }
            else
                setState(MediumState.INVALID);
        }
        else {
            setStone(MediumStone.NONE);
            setState(MediumState.INVALID);
        }
    }
}