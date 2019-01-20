package aurocosh.divinefavor.common.custom_data.player.interaction_handler;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.util.UtilSerialize;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

// Handles the actual read/write of the nbt.
public class InteractionStorage implements Capability.IStorage<IInteractionHandler> {
    private static final String TAG_LAST_CLICKED_POSITIONS = "LastClicked";

    @Override
    public NBTBase writeNBT (Capability<IInteractionHandler> capability, IInteractionHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();

        List<Vector3i> clickedPositions = instance.getLastClickedPositions();
        List<BlockPos> posList = Vector3i.convert(clickedPositions);
        int[] clickedArray = UtilSerialize.SerializeBlockPosArray(posList);
        tag.setIntArray(TAG_LAST_CLICKED_POSITIONS, clickedArray);
        return tag;
    }

    @Override
    public void readNBT (Capability<IInteractionHandler> capability, IInteractionHandler instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;

        int[] posArray = tag.getIntArray(TAG_LAST_CLICKED_POSITIONS);
        List<BlockPos> posList = UtilSerialize.DeserializeBlockPosArray(posArray);
        List<Vector3i> clickedPositions = Vector3i.convertPos(posList);
        instance.setLastClickedPositions(clickedPositions);
    }
}
