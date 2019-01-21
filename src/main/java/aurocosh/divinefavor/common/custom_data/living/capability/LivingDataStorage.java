package aurocosh.divinefavor.common.custom_data.living.capability;

import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationDataSerializer;
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class LivingDataStorage implements Capability.IStorage<ILivingDataHandler> {
    private static final CurseDataSerializer curseSerializer = new CurseDataSerializer();
    private static final LimpLegDataSerializer limpLegDataSerializer = new LimpLegDataSerializer();
    private static final PetrificationDataSerializer petrificationSerializer = new PetrificationDataSerializer();
    private static final WindLeashDataSerializer windLeashSerializer = new WindLeashDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<ILivingDataHandler> capability, ILivingDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        curseSerializer.serialize(tag, instance.getCurseData());
        limpLegDataSerializer.serialize(tag, instance.getLimpLegData());
        petrificationSerializer.serialize(tag, instance.getPetrificationData());
        windLeashSerializer.serialize(tag, instance.getWindLeashData());
        return tag;
    }

    @Override
    public void readNBT(Capability<ILivingDataHandler> capability, ILivingDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        curseSerializer.deserialize(tag, instance.getCurseData());
        limpLegDataSerializer.deserialize(tag, instance.getLimpLegData());
        petrificationSerializer.deserialize(tag, instance.getPetrificationData());
        windLeashSerializer.deserialize(tag, instance.getWindLeashData());
    }
}
