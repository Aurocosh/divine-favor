package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.item.soul_shards.ItemSoulShard;
import aurocosh.divinefavor.common.muliblock.IMultiblockController;
import aurocosh.divinefavor.common.muliblock.ModMultiBlock;
import aurocosh.divinefavor.common.muliblock.common.ModMultiBlocks;
import aurocosh.divinefavor.common.muliblock.common.MultiblockWatcher;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TileSoulboundLectern extends TileEntity implements IMultiblockController {
    private final static String TAG_SHARD = "Shard";
    private final static String TAG_STATE_LECTERN = "StateLectern";
    private final static String TAG_GEM_LECTERN = "GemLectern";
    private final static Map<Integer, ModMultiBlock> multiblocks = new HashMap<>();

    static {
        multiblocks.put(ModSpirits.INSTANCE.getArbow().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_arbow());
        multiblocks.put(ModSpirits.INSTANCE.getBlizrabi().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_blizrabi());
        multiblocks.put(ModSpirits.INSTANCE.getEndererer().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_endererer());
        multiblocks.put(ModSpirits.INSTANCE.getLoon().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_loon());
        multiblocks.put(ModSpirits.INSTANCE.getNeblaze().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_neblaze());
        multiblocks.put(ModSpirits.INSTANCE.getRedwind().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_redwind());
        multiblocks.put(ModSpirits.INSTANCE.getRomol().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_romol());
        multiblocks.put(ModSpirits.INSTANCE.getSquarefury().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_squarefury());
        multiblocks.put(ModSpirits.INSTANCE.getTimber().getId(), ModMultiBlocks.INSTANCE.getSoulbound_lectern_timber());
    }

    private boolean isRejecting;
    private MultiBlockInstance multiBlockInstance;

    private SoulboundLecternState state = SoulboundLecternState.INACTIVE;
    private SoulboundLecternGem gem = SoulboundLecternGem.NONE;

    private ItemStackHandler shardStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemSoulShard && stack.getCount() == 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (!world.isRemote) {
                ItemStack stack = shardStackHandler.getStackInSlot(slot);
                if (!stack.isEmpty()) {
                    isRejecting = true;

                    IBlockState blockState = world.getBlockState(pos);
                    getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);

                    tryToFormMultiBlock();
                }
            }
            markDirty();
        }
    };

    public TileSoulboundLectern() {
        super();
        isRejecting = false;
    }

    @Override
    public void onLoad() {
        tryToFormMultiBlockInternal();
    }

    public ItemStackHandler getShardStackHandler() {
        return shardStackHandler;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        state = SoulboundLecternState.VALUES[compound.getInteger(TAG_STATE_LECTERN)];
        gem = SoulboundLecternGem.VALUES[compound.getInteger(TAG_GEM_LECTERN)];
        if (compound.hasKey(TAG_SHARD))
            shardStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_SHARD));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_STATE_LECTERN, state.ordinal());
        compound.setInteger(TAG_GEM_LECTERN, gem.ordinal());
        compound.setTag(TAG_SHARD, shardStackHandler.serializeNBT());
        return compound;
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot gainFavor it
        if (isRejecting) {
            isRejecting = false;
            return false;
        }
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    public boolean isMultiblockValid() {
        return multiBlockInstance != null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return facing == null;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(shardStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setTag(TAG_SHARD, shardStackHandler.serializeNBT());
        nbtTag.setInteger(TAG_STATE_LECTERN, state.ordinal());
        nbtTag.setInteger(TAG_GEM_LECTERN, gem.ordinal());
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
        shardStackHandler.deserializeNBT(compound.getCompoundTag(TAG_SHARD));

        int stateIndex = compound.getInteger(TAG_STATE_LECTERN);
        int gemIndex = compound.getInteger(TAG_GEM_LECTERN);
        if (stateIndex != state.ordinal() || gemIndex != gem.ordinal()) {
            gem = SoulboundLecternGem.VALUES[gemIndex];
            state = SoulboundLecternState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public ItemStack getShardStack() {
        return shardStackHandler.getStackInSlot(0);
    }

    public int getFavorId() {
        ItemStack stack = getShardStack();
        if (stack.isEmpty())
            return -1;
        ItemSoulShard soulShard = (ItemSoulShard) stack.getItem();
        return soulShard.getSpirit().getId();
    }

    public SoulboundLecternState getState() {
        return state;
    }

    public SoulboundLecternGem getGem() {
        return gem;
    }

    public void setState(SoulboundLecternState state) {
        if (this.state == state)
            return;
        this.state = state;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    public void setGem(SoulboundLecternGem gem) {
        if (this.gem == gem)
            return;
        this.gem = gem;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    @Override
    public MultiBlockInstance getMultiblockInstance() {
        return multiBlockInstance;
    }

    @Override
    public void multiblockDeconstructed() {
        MultiblockWatcher.INSTANCE.unRegisterController(this);
        setState(SoulboundLecternState.INACTIVE);
        multiBlockInstance = null;
    }

    @Override
    public void multiblockDamaged(EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        multiblockDeconstructed();
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
        ItemStack stack = getShardStack();
        if (stack.isEmpty())
            return;
        ItemSoulShard soulShard = (ItemSoulShard) stack.getItem();
        ModMultiBlock multiBlock = multiblocks.get(soulShard.getSpirit().getId());
        if (multiBlock != null) {
            multiBlockInstance = multiBlock.makeMultiBlock(world, pos);
            if (multiBlockInstance != null)
                MultiblockWatcher.INSTANCE.registerController(this);
        }
    }

    private void updateState() {
        ItemStack stack = getShardStack();
        if (stack.isEmpty())
            return;
        ItemSoulShard soulShard = (ItemSoulShard) stack.getItem();
        ModSpirit spirit = soulShard.getSpirit();
        if (spirit == ModSpirits.INSTANCE.getEndererer())
            setGem(SoulboundLecternGem.END);
        else if (spirit == ModSpirits.INSTANCE.getRomol())
            setGem(SoulboundLecternGem.MIND);
        else if (spirit == ModSpirits.INSTANCE.getNeblaze())
            setGem(SoulboundLecternGem.NETHER);
        else if (spirit == ModSpirits.INSTANCE.getArbow())
            setGem(SoulboundLecternGem.PEACE);
        else if (spirit == ModSpirits.INSTANCE.getRedwind())
            setGem(SoulboundLecternGem.WILL);
        else if (spirit == ModSpirits.INSTANCE.getLoon())
            setGem(SoulboundLecternGem.UNDEATH);
        else if (spirit == ModSpirits.INSTANCE.getBlizrabi())
            setGem(SoulboundLecternGem.WATER);
        else if (spirit == ModSpirits.INSTANCE.getSquarefury())
            setGem(SoulboundLecternGem.WILD);
        else if (spirit == ModSpirits.INSTANCE.getTimber())
            setGem(SoulboundLecternGem.WITHER);
        else
            setGem(SoulboundLecternGem.NONE);

        if (multiBlockInstance == null)
            setState(SoulboundLecternState.INACTIVE);
        else
            setState(SoulboundLecternState.ACTIVE);
    }
}