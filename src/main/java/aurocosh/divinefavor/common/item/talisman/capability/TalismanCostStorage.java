package aurocosh.divinefavor.common.item.talisman.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class TalismanCostStorage implements Capability.IStorage<ITalismanCostHandler> {
    @Override
    public NBTBase writeNBT (Capability<ITalismanCostHandler> capability, ITalismanCostHandler instance, EnumFacing side) {

        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("unitIndex", instance.getSelectedUnitIndex());
        tag.setInteger("costIndex", instance.getSelectedCostIndex());
        return tag;
    }

    @Override
    public void readNBT (Capability<ITalismanCostHandler> capability, ITalismanCostHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.setUnitIndex(tag.getInteger("unitIndex"));
        instance.setCostIndex(tag.getInteger("costIndex"));
    }
}
