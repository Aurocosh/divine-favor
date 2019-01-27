package aurocosh.divinefavor.common.custom_data.player.data.favor;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

// Handles the actual read/write of the nbt.
public class FavorDataSerializer implements INbtSerializer<FavorData> {
    public static final String TAG_FAVOR_VALUES = "FavorValue";

    public static NBTTagCompound getNbtTagCompound(FavorData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        FavorValue[] favorValues = instance.getFavorValues();
        List<ModFavor> favors = ModMappers.favors.getValues();
        for (int i = 0; i < favorValues.length; i++) {
            FavorValue favorValue = favorValues[i];
            int[] favorSerialized = new int[]{
                    favorValue.getValue(),
                    favorValue.getRegen(),
                    favorValue.getMinLimit(),
                    favorValue.getMaxLimit()};
            ModFavor favor = favors.get(i);
            tag.setIntArray(favor.getName(), favorSerialized);
        }
        return tag;
    }

    public static void setDataFromNBT(FavorData instance, NBTTagCompound nbt) {
        List<ModFavor> talismans = ModMappers.favors.getValues();
        FavorValue[] favorValues = instance.getFavorValues();
        for (ModFavor favor : talismans) {
            if (!nbt.hasKey(favor.getName()))
                continue;
            int[] favorSerialized = nbt.getIntArray(favor.getName());
            if(favorSerialized.length == 4)
                favorValues[favor.getId()] = new FavorValue(favorSerialized[0], favorSerialized[1], favorSerialized[2], favorSerialized[3]);
        }
        instance.setFavorValues(favorValues);
    }

    @Override
    public void serialize(NBTTagCompound nbt, FavorData instance) {
        NBTTagCompound usesTag = getNbtTagCompound(instance);
        nbt.setTag(TAG_FAVOR_VALUES, usesTag);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, FavorData instance) {
        if (!nbt.hasKey(TAG_FAVOR_VALUES))
            return;
        NBTTagCompound usesTag = nbt.getCompoundTag(TAG_FAVOR_VALUES);
        setDataFromNBT(instance, usesTag);
    }
}
