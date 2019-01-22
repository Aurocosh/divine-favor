package aurocosh.divinefavor.common.custom_data.player.data.molten_skin;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

// Handles the actual read/write of the nbt.
public class MoltenSkinDataSerializer implements INbtSerializer<MoltenSkinData> {
    private static final String TAG_TIME_OUTSIDE_LAVA = "TimeOutsideLava";

    @Override
    public void serialize(NBTTagCompound nbt, MoltenSkinData instance) {
        nbt.setInteger(TAG_TIME_OUTSIDE_LAVA, instance.getTicks());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, MoltenSkinData instance) {
        instance.setTime(nbt.getInteger(TAG_TIME_OUTSIDE_LAVA));
    }
}
