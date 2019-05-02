package aurocosh.divinefavor.common.custom_data.world.capability;

import aurocosh.divinefavor.common.custom_data.world.data.altars.AltarsDataSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class WorldDataStorage implements Capability.IStorage<IWorldDataHandler> {
    private static final AltarsDataSerializer ALTARS_DATA_SERIALIZER = new AltarsDataSerializer();

    @Override
    public NBTBase writeNBT(Capability<IWorldDataHandler> capability, IWorldDataHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        ALTARS_DATA_SERIALIZER.serialize(tag, instance.getAltarData());
        return tag;
    }

    @Override
    public void readNBT(Capability<IWorldDataHandler> capability, IWorldDataHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        ALTARS_DATA_SERIALIZER.deserialize(tag, instance.getAltarData());
    }
}
