package aurocosh.divinefavor.common.custom_data.player.data.interaction_handler;

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.util.UtilSerialize;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.List;

// Handles the actual read/write of the nbt.
public class InteractionDataSerializer implements INbtSerializer<InteractionData> {
    private static final String TAG_LAST_CLICKED_POSITIONS = "LastClickedPositions";

    @Override
    public void serialize(NBTTagCompound nbt, InteractionData instance) {
        List<Vector3i> clickedPositions = instance.getLastClickedPositions();
        List<BlockPos> posList = Vector3i.convert(clickedPositions);
        int[] clickedArray = UtilSerialize.SerializeBlockPosArray(posList);
        nbt.setIntArray(TAG_LAST_CLICKED_POSITIONS, clickedArray);
    }

    @Override
    public void deserialize(NBTTagCompound nbt, InteractionData instance) {
        int[] posArray = nbt.getIntArray(TAG_LAST_CLICKED_POSITIONS);
        List<BlockPos> posList = UtilSerialize.DeserializeBlockPosArray(posArray);
        List<Vector3i> clickedPositions = Vector3i.convertPos(posList);
        instance.setLastClickedPositions(clickedPositions);
    }
}
