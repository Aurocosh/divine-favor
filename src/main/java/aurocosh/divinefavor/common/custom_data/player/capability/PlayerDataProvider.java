package aurocosh.divinefavor.common.custom_data.player.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerDataProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPlayerDataHandler instance = PlayerDataDataHandler.CAPABILITY_PLAYER_DATA.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PlayerDataDataHandler.CAPABILITY_PLAYER_DATA;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? PlayerDataDataHandler.CAPABILITY_PLAYER_DATA.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) PlayerDataDataHandler.CAPABILITY_PLAYER_DATA.getStorage().writeNBT(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        PlayerDataDataHandler.CAPABILITY_PLAYER_DATA.getStorage().readNBT(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA, instance, null, nbt);
    }
}
