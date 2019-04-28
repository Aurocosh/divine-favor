package aurocosh.divinefavor.common.block.bath_heater;

import aurocosh.divinefavor.common.area.IAreaWatcher;
import aurocosh.divinefavor.common.area.WorldArea;
import aurocosh.divinefavor.common.area.WorldAreaWatcher;
import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend;
import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TileBathHeater extends TileEntity implements ITickable, IAreaWatcher {
    public static final int FUEL_SIZE = 1;
    public static final int INGREDIENTS_SIZE = 1;

    private static final int MAX_PROGRESS = 100;
    private static final int RADIUS_OF_EFFECT = 3;

    private static final String TAG_FUEL = "FUEL";
    private static final String TAG_MAX_BURN_TIME = "MaxBurnTime";
    private static final String TAG_CURRENT_BURN_TIME = "CurrentBurnTime";
    private static final String TAG_INGREDIENTS = "Ingredients";
    private static final String TAG_STATE_HEATER = "StateHeater";
    private static final String TAG_WATER_POSITIONS = "WaterPositions";
    private static final String TAG_EFFECT_TICK_RATE = "EffectTickRate";

    private int progressBurning;
    private int clientProgressBurning;
    private int maxBurnTime;
    private int currentBurnTime;

    private int progressEffect;
    private int clientProgressEffect;
    private int maxEffectTime;
    private int currentEffectTime;
    private ItemBathingBlend activeBlend;

    private boolean refresh;
    private boolean initialized;

    private final WorldArea area;
    private final LoopedCounter loopedCounter;
    private final Set<BlockPos> waterPositions;
    private BathHeaterState state = BathHeaterState.INACTIVE;

    private ItemStackHandler fuelStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return TileEntityFurnace.getItemBurnTime(stack) > 0;
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    private ItemStackHandler blendStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemBathingBlend;
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    public TileBathHeater() {
        super();

        progressBurning = 0;
        clientProgressBurning = 0;
        maxBurnTime = 0;
        currentBurnTime = 0;

        maxEffectTime = 0;
        currentEffectTime = 0;
        activeBlend = null;

        initialized = false;
        refresh = true;
        area = new WorldArea();
        waterPositions = new HashSet<>();
        loopedCounter = new LoopedCounter();
    }

    public void initialize() {
        if (initialized)
            return;
        initialized = true;
        area.addPositions(UtilCoordinates.INSTANCE.getBlocksInRadius(pos, RADIUS_OF_EFFECT, 0, RADIUS_OF_EFFECT));
        WorldAreaWatcher.registerWatcher(this);
    }

    private void refreshWaterBlocks() {
        if (!refresh)
            return;
        refresh = false;
        waterPositions.clear();
        BlockPos posVector = new BlockPos(pos);
        List<BlockPos> start = new ArrayList<>();
        start.add(posVector.add(BlockPosConstants.EAST));
        start.add(posVector.add(BlockPosConstants.WEST));
        start.add(posVector.add(BlockPosConstants.NORTH));
        start.add(posVector.add(BlockPosConstants.SOUTH));

        List<BlockPos> waterPosList = UtilCoordinates.INSTANCE.floodFill(start, BlockPosConstants.HORIZONTAL_DIRECT, this::isWater, 50);
        UtilList.filterList(waterPosList, area::isApartOfArea);
        waterPositions.addAll(waterPosList);

        IBlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    private boolean isWater(BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return UtilBlock.isWater(block);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        maxBurnTime = compound.getInteger(TAG_MAX_BURN_TIME);
        currentBurnTime = compound.getInteger(TAG_CURRENT_BURN_TIME);
        progressBurning = maxBurnTime == 0 ? 0 : currentBurnTime / maxBurnTime;
        state = BathHeaterState.VALUES[compound.getInteger(TAG_STATE_HEATER)];
        waterPositions.clear();
        waterPositions.addAll(UtilBlockPos.deserialize(compound.getIntArray(TAG_WATER_POSITIONS)));
        loopedCounter.setTickRate(compound.getInteger(TAG_EFFECT_TICK_RATE));
        if (compound.hasKey(TAG_FUEL))
            fuelStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_FUEL));
        if (compound.hasKey(TAG_INGREDIENTS))
            blendStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_INGREDIENTS));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_MAX_BURN_TIME, maxBurnTime);
        compound.setInteger(TAG_CURRENT_BURN_TIME, currentBurnTime);
        compound.setInteger(TAG_STATE_HEATER, state.ordinal());
        compound.setIntArray(TAG_WATER_POSITIONS, UtilBlockPos.serialize(waterPositions));
        compound.setInteger(TAG_EFFECT_TICK_RATE, loopedCounter.getTickRate());
        compound.setTag(TAG_FUEL, fuelStackHandler.serializeNBT());
        compound.setTag(TAG_INGREDIENTS, blendStackHandler.serializeNBT());
        return compound;
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot gainFavor it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return facing == EnumFacing.UP || facing == EnumFacing.DOWN;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(blendStackHandler);
            else if (facing == EnumFacing.DOWN)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuelStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger(TAG_STATE_HEATER, state.ordinal());
        nbtTag.setIntArray(TAG_WATER_POSITIONS, UtilBlockPos.serialize(waterPositions));
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger(TAG_STATE_HEATER);

        if (!world.isRemote)
            return;
        if (stateIndex != state.ordinal()) {
            state = BathHeaterState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
        waterPositions.clear();
        waterPositions.addAll(UtilBlockPos.deserialize(packet.getNbtCompound().getIntArray(TAG_WATER_POSITIONS)));
    }

    public boolean isBurning() {
        return state == BathHeaterState.ACTIVE;
    }

    public BathHeaterState getState() {
        return state;
    }

    public void setState(BathHeaterState state) {
        if (this.state == state)
            return;

        this.state = state;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    @Override
    public void update() {
        initialize();
        if (!world.isRemote) {
            refreshWaterBlocks();
            burn();
            applyEffect();
        }
        else
            bubble();
    }

    private void bubble() {
        if (state == BathHeaterState.INACTIVE)
            return;
        for (BlockPos waterPos : waterPositions) {
            Random random = UtilRandom.random;
            world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, waterPos.getX() + random.nextDouble(), waterPos.getY() + random.nextDouble(), waterPos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    private void burn() {
        if (currentBurnTime > 0) {
            currentBurnTime--;
            progressBurning = (int) ((currentBurnTime / (float) maxBurnTime) * 100);
            setState(BathHeaterState.ACTIVE);
            markDirty();
            return;
        }
        else if (currentBurnTime == 0) {
            ItemStack stack = fuelStackHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                maxBurnTime = TileEntityFurnace.getItemBurnTime(stack);
                currentBurnTime = maxBurnTime;
                progressBurning = MAX_PROGRESS;
                stack.shrink(1);
                setState(BathHeaterState.ACTIVE);
                markDirty();
                return;
            }
        }
        progressBurning = 0;
        setState(BathHeaterState.INACTIVE);
    }

    private void applyEffect() {
        if (!isBurning())
            return;
        if (!loopedCounter.tick())
            return;
        if (currentEffectTime <= 0) {
            ItemStack stack = blendStackHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                activeBlend = (ItemBathingBlend) stack.getItem();
                maxEffectTime = ItemBathingBlend.Companion.getDuration(stack);
                loopedCounter.setTickRate(ItemBathingBlend.Companion.getRate(stack));
                currentEffectTime = maxEffectTime;
                progressEffect = 100;
                stack.shrink(1);
                markDirty();
            }
        }
        else {
            currentEffectTime--;
            progressEffect = (int) ((currentEffectTime / (float) maxEffectTime) * 100);
            markDirty();

            AxisAlignedBB axis = new AxisAlignedBB(pos.getX() - RADIUS_OF_EFFECT, pos.getY() + 1, pos.getZ() - RADIUS_OF_EFFECT, pos.getX() + RADIUS_OF_EFFECT, pos.getY(), pos.getZ() + RADIUS_OF_EFFECT);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axis, (EntityLivingBase e) -> e != null && e.isInWater());

            for (EntityLivingBase livingBase : list)
                activeBlend.applyEffect(livingBase);
        }
    }

    public int getProgressBurning() {
        return progressBurning;
    }

    public int getClientProgressBurning() {
        return clientProgressBurning;
    }

    public void setClientProgressBurning(int clientProgressBurning) {
        this.clientProgressBurning = clientProgressBurning;
    }


    public int getProgressEffect() {
        return progressEffect;
    }

    public int getClientProgressEffect() {
        return clientProgressEffect;
    }

    public void setClientProgressEffect(int clientProgressEffect) {
        this.clientProgressEffect = clientProgressEffect;
    }


    @Override
    public WorldArea getArea() {
        return area;
    }

    @Override
    public void blockChanged(World world, BlockPos changedPos, IBlockState state) {
        refresh = true;
    }
}