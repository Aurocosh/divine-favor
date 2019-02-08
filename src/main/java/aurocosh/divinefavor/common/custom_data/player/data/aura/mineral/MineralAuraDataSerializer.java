package aurocosh.divinefavor.common.custom_data.player.data.aura.mineral;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class MineralAuraDataSerializer implements INbtSerializer<MineralAuraData> {
    private static final String TAG_STONE_TO_BREAK = "StoneToBreak";

    @Override
    public void serialize(NBTTagCompound nbt, MineralAuraData instance) {
        nbt.setInteger(TAG_STONE_TO_BREAK, instance.getCount());
    }

    @Override
    public void deserialize(NBTTagCompound nbt, MineralAuraData instance) {
        instance.setCount(nbt.getInteger(TAG_STONE_TO_BREAK));
    }
}
