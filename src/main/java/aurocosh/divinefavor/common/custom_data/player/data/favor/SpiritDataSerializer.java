package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class SpiritDataSerializer implements INbtSerializer<SpiritData> {
    private static final String TAG_SPIRIT_DATA = "SpiritData";
    private static final String TAG_CONTRACTS = "Contracts";

    public static NBTTagCompound getNbtTagCompound(SpiritData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        for (ModSpirit spirit : ModMappers.spirits.getValues())
            tag.setInteger(spirit.getName(), instance.getFavor(spirit.getId()));
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts());
        return tag;
    }

    public static void setDataFromNBT(SpiritData instance, NBTTagCompound nbt) {
        if (nbt.hasKey(TAG_CONTRACTS))
            instance.deserializeContracts(nbt.getCompoundTag(TAG_CONTRACTS));
        for (ModSpirit spirit : ModMappers.spirits.getValues())
            instance.setFavor(spirit.getId(), nbt.getInteger(spirit.getName()));
    }

    @Override
    public void serialize(NBTTagCompound nbt, SpiritData instance) {
        NBTTagCompound usesTag = getNbtTagCompound(instance);
        nbt.setTag(TAG_SPIRIT_DATA, usesTag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, SpiritData instance) {
        if (!nbt.hasKey(TAG_SPIRIT_DATA))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_SPIRIT_DATA);
        setDataFromNBT(instance, usesTag);
    }
}
