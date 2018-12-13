package aurocosh.divinefavor.common.player_data.favor;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Collection;

// Handles the actual read/write of the nbt.
public class FavorStorage implements Capability.IStorage<IFavorHandler> {
    @Override
    public NBTBase writeNBT (Capability<IFavorHandler> capability, IFavorHandler instance, EnumFacing side) {
        return getNbtTagCompound(instance);
    }

    @Override
    public void readNBT (Capability<IFavorHandler> capability, IFavorHandler instance, EnumFacing side, NBTBase nbt) {
        setDataFromNBT(instance, (NBTTagCompound) nbt);
    }

    public static NBTTagCompound getNbtTagCompound(IFavorHandler instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        for (ModFavor favor : ModRegistries.favors.getValues()) {
            int value = instance.getFavor(favor.id);
            tag.setInteger(favor.tag,value);
        }
        return tag;
    }

    public static void setDataFromNBT(IFavorHandler instance, NBTTagCompound nbt) {
        for (ModFavor favor : ModRegistries.favors.getValues()) {
            int value = nbt.getInteger(favor.tag);
            instance.setFavor(favor.id,value);
        }
    }
}
