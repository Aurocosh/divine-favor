package aurocosh.divinefavor.common.custom_data.player.data.interaction_handler;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.util.UtilSerialize;
import net.minecraft.nbt.NBTTagCompound;

// Handles the actual read/write of the nbt.
public class InteractionDataSerializer implements INbtSerializer<InteractionData> {
    private static final String TAG_LAST_CLICKED_POSITIONS = "LastClickedPositions";

    @Override
    public void serialize(NBTTagCompound nbt, InteractionData instance) {
        int[] clickedArray = UtilSerialize.serializeBlockPosArray(instance.getLastClickedPositions());
        nbt.setIntArray(TAG_LAST_CLICKED_POSITIONS, clickedArray);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, InteractionData instance) {
        int[] posArray = nbt.getIntArray(TAG_LAST_CLICKED_POSITIONS);
        instance.setLastClickedPositions(UtilSerialize.deserializeBlockPosArray(posArray));
    }
}
