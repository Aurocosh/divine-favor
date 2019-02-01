package aurocosh.divinefavor.common.custom_data.player.data.contracts;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class ContracsDataSerializer implements INbtSerializer<ContractsData> {
    private static String TAG_CONTRACTS = "Contracts";

    @Override
    public void serialize(NBTTagCompound nbt, ContractsData instance) {
        nbt.setTag(TAG_CONTRACTS, instance.getStackHandler().serializeNBT());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, ContractsData instance) {
        if (nbt.hasKey(TAG_CONTRACTS))
            instance.getStackHandler().deserializeNBT(nbt.getCompoundTag(TAG_CONTRACTS));
    }
}
