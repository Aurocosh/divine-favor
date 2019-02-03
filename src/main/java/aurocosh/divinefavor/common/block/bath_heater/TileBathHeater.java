package aurocosh.divinefavor.common.block.bath_heater;

import aurocosh.divinefavor.common.area.IAreaWatcher;
import aurocosh.divinefavor.common.area.WorldArea;
import aurocosh.divinefavor.common.area.WorldAreaWatcher;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilSerialize;
import aurocosh.divinefavor.common.util.UtilVector3i;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
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
    public static final int INGREDIENTS_SIZE = 4;

    private static final int MAX_PROGRESS = 100;
    private static final int RADIUS_OF_EFFECT = 3;

    private static final String TAG_FUEL = "FUEL";
    private static final String TAG_MAX_BURN_TIME = "MaxBurnTime";
    private static final String TAG_CURRENT_BURN_TIME = "CurrentBurnTime";
    private static final String TAG_INGREDIENTS = "Ingredients";
    private static final String TAG_STATE_HEATER = "StateHeater";
    private static final String TAG_WATER_POSITIONS = "WaterPositions";

    private int progress;
    private int clientProgress;
    private int maxBurnTime;
    private int currentBurnTime;
    private boolean refresh;
    private boolean initialized;

    private final WorldArea area;
    private final Set<Vector3i> waterPositions;
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

    private ItemStackHandler ingridientsStackHandler = new ItemStackHandler(4) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return true;
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    public TileBathHeater() {
        super();
        initialized = false;
        refresh = true;
        area = new WorldArea();
        waterPositions = new HashSet<>();
    }

    public void initialize() {
        if (initialized)
            return;
        initialized = true;
        area.addPositions(UtilCoordinates.getBlocksInRadius(pos, RADIUS_OF_EFFECT, 0, RADIUS_OF_EFFECT));
        WorldAreaWatcher.registerWatcher(this);
    }

    private void refreshWaterBlocks() {
        if (!refresh)
            return;
        refresh = false;
        waterPositions.clear();
        Vector3i posVector = new Vector3i(pos);
        List<Vector3i> start = new ArrayList<>();
        start.add(posVector.add(Vector3i.EAST));
        start.add(posVector.add(Vector3i.WEST));
        start.add(posVector.add(Vector3i.NORTH));
        start.add(posVector.add(Vector3i.SOUTH));

        List<BlockPos> posList = UtilCoordinates.floodFill(start, UtilVector3i.getNeighbourDirectHorizontal(), this::isWater, 50);
        for (BlockPos blockPos : posList) {
            Vector3i vector = new Vector3i(blockPos);
            if (area.isApartOfArea(vector))
                waterPositions.add(vector);
        }
        IBlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    private boolean isWater(BlockPos pos){
        Block block = world.getBlockState(pos).getBlock();
        return block == Blocks.WATER || block == Blocks.FLOWING_WATER;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        maxBurnTime = compound.getInteger(TAG_MAX_BURN_TIME);
        currentBurnTime = compound.getInteger(TAG_CURRENT_BURN_TIME);
        progress = maxBurnTime == 0 ? 0 : currentBurnTime / maxBurnTime;
        state = BathHeaterState.VALUES[compound.getInteger(TAG_STATE_HEATER)];
        waterPositions.clear();
        waterPositions.addAll(UtilSerialize.deserializeVector3i(compound.getIntArray(TAG_WATER_POSITIONS)));
        if (compound.hasKey(TAG_FUEL))
            fuelStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_FUEL));
        if (compound.hasKey(TAG_INGREDIENTS))
            ingridientsStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_INGREDIENTS));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_MAX_BURN_TIME, maxBurnTime);
        compound.setInteger(TAG_CURRENT_BURN_TIME, currentBurnTime);
        compound.setInteger(TAG_STATE_HEATER, state.ordinal());
        compound.setIntArray(TAG_WATER_POSITIONS, UtilSerialize.serializeVector3i(waterPositions));
        compound.setTag(TAG_FUEL, fuelStackHandler.serializeNBT());
        compound.setTag(TAG_INGREDIENTS, ingridientsStackHandler.serializeNBT());
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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ingridientsStackHandler);
            else if (facing == EnumFacing.DOWN)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuelStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger(TAG_STATE_HEATER, state.ordinal());
        nbtTag.setIntArray(TAG_WATER_POSITIONS, UtilSerialize.serializeVector3i(waterPositions));
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
        waterPositions.addAll(UtilSerialize.deserializeVector3i(packet.getNbtCompound().getIntArray(TAG_WATER_POSITIONS)));
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
        }
        else
            bubble();
    }

    private void bubble() {
        if (state == BathHeaterState.INACTIVE)
            return;
        for (Vector3i waterPos : waterPositions) {
            Random random = UtilRandom.random;
            world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, waterPos.x + random.nextDouble(), waterPos.y + random.nextDouble(), waterPos.z + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    private void burn() {
        if (currentBurnTime > 0) {
            currentBurnTime--;
            progress = (int) ((currentBurnTime / (float) maxBurnTime) * 100);
            setState(BathHeaterState.ACTIVE);
            markDirty();
            return;
        }
        if (currentBurnTime == 0) {
            ItemStack stack = fuelStackHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                maxBurnTime = TileEntityFurnace.getItemBurnTime(stack);
                currentBurnTime = maxBurnTime;
                progress = MAX_PROGRESS;
                stack.shrink(1);
                setState(BathHeaterState.ACTIVE);
                markDirty();
                return;
            }
        }
        progress = 0;
        setState(BathHeaterState.INACTIVE);
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
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