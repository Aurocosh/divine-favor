package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class SpiritDataSerializer implements INbtSerializer<SpiritData> {
    private static final String TAG_SPIRIT_DATA = "SpiritData";
    private static final String TAG_CONTRACTS = "Contracts";

    @Override
    public void serialize(NBTTagCompound nbt, SpiritData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        for (ModSpirit spirit : ModMappers.spirits.getValues())
            tag.setInteger(spirit.getName(), instance.getFavor(spirit.getId()));
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts());
        nbt.setTag(TAG_SPIRIT_DATA, tag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, SpiritData instance) {
        if (!nbt.hasKey(TAG_SPIRIT_DATA))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_SPIRIT_DATA);
        if (usesTag.hasKey(TAG_CONTRACTS))
            instance.deserializeContracts(usesTag.getCompoundTag(TAG_CONTRACTS));
        for (ModSpirit spirit : ModMappers.spirits.getValues())
            instance.setFavor(spirit.getId(), usesTag.getInteger(spirit.getName()));
    }
}
