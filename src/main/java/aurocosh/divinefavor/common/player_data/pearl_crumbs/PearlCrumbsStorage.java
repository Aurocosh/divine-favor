package aurocosh.divinefavor.common.player_data.pearl_crumbs;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.util.UtilSerialize;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

// Handles the actual read/write of the nbt.
public class PearlCrumbsStorage implements Capability.IStorage<IPearlCrumbsHandler> {
    private static String TAG_CRUMBS_POSITIONS = "CrumbsPositions";
    private static String TAG_CRUMBS_DIMENSIONS = "CrumbsDimensions";

    @Override
    public NBTBase writeNBT(Capability<IPearlCrumbsHandler> capability, IPearlCrumbsHandler instance, EnumFacing side) {
        final NBTTagCompound tag = new NBTTagCompound();
        List<GlobalBlockPos> globalBlockPos = instance.getAllPositions();
        List<BlockPos> posList = new ArrayList<>(globalBlockPos.size());
        int[] dimensions = new int[globalBlockPos.size()];

        for (int i = 0; i < globalBlockPos.size(); i++) {
            GlobalBlockPos pos = globalBlockPos.get(i);
            posList.add(pos.pos);
            dimensions[i] = pos.dimensionId;
        }

        tag.setIntArray(TAG_CRUMBS_POSITIONS, UtilSerialize.SerializeBlockPosArray(posList));
        tag.setIntArray(TAG_CRUMBS_DIMENSIONS, dimensions);
        return tag;
    }

    @Override
    public void readNBT(Capability<IPearlCrumbsHandler> capability, IPearlCrumbsHandler instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;

        List<BlockPos> posList = UtilSerialize.DeserializeBlockPosArray(tag.getIntArray(TAG_CRUMBS_POSITIONS));
        int[] dimensions = tag.getIntArray(TAG_CRUMBS_DIMENSIONS);

        if (posList.size() != dimensions.length)
            return;

        List<GlobalBlockPos> globalBlockPos = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            BlockPos pos = posList.get(i);
            int dimension = dimensions[i];
            globalBlockPos.add(new GlobalBlockPos(pos, dimension));
        }
        instance.setAllPositions(globalBlockPos);
    }
}
