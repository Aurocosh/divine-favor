package aurocosh.divinefavor.common.block.soulbound_lectern;

import aurocosh.divinefavor.common.item.ItemBloodCrystal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileSoulboundLectern extends TileEntity {
    public static final int SIZE = 27;
    private boolean isRejecting;
    private final String TAG_BLOOD_CRYSTAL = "BloodCrystal";
    private final String TAG_STATE_LECTERN = "StateLectern";

    private SoulboundLecternState state = SoulboundLecternState.UNBOUND;

    private ItemStackHandler bloodCrystalStackHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemBloodCrystal && ItemBloodCrystal.hasOwner(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileSoulboundLectern.this.markDirty();
            bindToPlayer();
        }
    };

    public TileSoulboundLectern() {
        super();
        isRejecting = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        state = SoulboundLecternState.VALUES[compound.getInteger(TAG_STATE_LECTERN)];
        if (compound.hasKey(TAG_BLOOD_CRYSTAL))
            bloodCrystalStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(TAG_BLOOD_CRYSTAL));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(TAG_STATE_LECTERN, state.ordinal());
        compound.setTag(TAG_BLOOD_CRYSTAL, bloodCrystalStackHandler.serializeNBT());
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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(bloodCrystalStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger(TAG_STATE_LECTERN, state.ordinal());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger(TAG_STATE_LECTERN);

        if (world.isRemote && stateIndex != state.ordinal()) {
            state = SoulboundLecternState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public SoulboundLecternState getState() {
        return state;
    }

    public void setState(SoulboundLecternState state) {
        if (this.state == state)
            return;

        this.state = state;
        markDirty();
        IBlockState blockState = world.getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    public void bindToPlayer() {
        isRejecting = true;
    }
}