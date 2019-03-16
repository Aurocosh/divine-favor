package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

// Handles the actual read/write of the nbt.
public class FavorDataSerializer implements INbtSerializer<FavorData> {
    private static final String TAG_FAVORS = "Favors";
    private static final String TAG_CONTRACTS = "Contracts";

    public static NBTTagCompound getNbtTagCompound(FavorData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        int[] favorValues = instance.getFavorValues();
        List<ModFavor> favors = ModMappers.favors.getValues();
        for (int i = 0; i < favorValues.length; i++) {
            ModFavor favor = favors.get(i);
            tag.setInteger(favor.getName(), favorValues[i]);
        }
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts());
        return tag;
    }

    public static void setDataFromNBT(FavorData instance, NBTTagCompound nbt) {
        if (nbt.hasKey(TAG_CONTRACTS))
            instance.deserializeContracts(nbt.getCompoundTag(TAG_CONTRACTS));

        List<ModFavor> favors = ModMappers.favors.getValues();
        int[] favorValues = new int[favors.size()];
        for (int i = 0; i < favorValues.length; i++) {
            ModFavor favor = favors.get(i);
            favorValues[i] = nbt.getInteger(favor.getName());
        }
        instance.setFavorValues(favorValues);
    }

    @Override
    public void serialize(NBTTagCompound nbt, FavorData instance) {
        NBTTagCompound usesTag = getNbtTagCompound(instance);
        nbt.setTag(TAG_FAVORS, usesTag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, FavorData instance) {
        if (!nbt.hasKey(TAG_FAVORS))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_FAVORS);
        setDataFromNBT(instance, usesTag);
    }
}
