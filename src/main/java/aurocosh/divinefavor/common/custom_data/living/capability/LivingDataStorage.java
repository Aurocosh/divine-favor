package aurocosh.divinefavor.common.custom_data.living.capability;

import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class LivingDataStorage implements Capability.IStorage<ILivingDataHandler> {
    private static final CurseDataSerializer CURSE_DATA_SERIALIZER = new CurseDataSerializer();
    private static final LimpLegDataSerializer LIMP_LEG_DATA_SERIALIZER = new LimpLegDataSerializer();
    private static final PetrificationDataSerializer PETRIFICATION_DATA_SERIALIZER = new PetrificationDataSerializer();
    private static final SoulTheftDataSerializer SOUL_THEFT_DATA_SERIALIZER = new SoulTheftDataSerializer();
    private static final SuffocatingFumesDataSerializer SUFFOCATING_FUMES_DATA_SERIALIZER = new SuffocatingFumesDataSerializer();
    private static final WindLeashDataSerializer WIND_LEASH_DATA_SERIALIZER = new WindLeashDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<ILivingDataHandler> capability, ILivingDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        CURSE_DATA_SERIALIZER.serialize(tag, instance.getCurseData());
        LIMP_LEG_DATA_SERIALIZER.serialize(tag, instance.getLimpLegData());
        PETRIFICATION_DATA_SERIALIZER.serialize(tag, instance.getPetrificationData());
        SOUL_THEFT_DATA_SERIALIZER.serialize(tag, instance.getSoulTheftData());
        SUFFOCATING_FUMES_DATA_SERIALIZER.serialize(tag, instance.getSuffocatingFumesData());
        WIND_LEASH_DATA_SERIALIZER.serialize(tag, instance.getWindLeashData());
        return tag;
    }

    @Override
    public void readNBT(Capability<ILivingDataHandler> capability, ILivingDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        CURSE_DATA_SERIALIZER.deserialize(tag, instance.getCurseData());
        LIMP_LEG_DATA_SERIALIZER.deserialize(tag, instance.getLimpLegData());
        PETRIFICATION_DATA_SERIALIZER.deserialize(tag, instance.getPetrificationData());
        SOUL_THEFT_DATA_SERIALIZER.deserialize(tag, instance.getSoulTheftData());
        SUFFOCATING_FUMES_DATA_SERIALIZER.deserialize(tag, instance.getSuffocatingFumesData());
        WIND_LEASH_DATA_SERIALIZER.deserialize(tag, instance.getWindLeashData());
    }
}
