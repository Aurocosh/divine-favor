package aurocosh.divinefavor.common.util.helper_classes;

import aurocosh.divinefavor.common.util.UtilBlockPos;
import aurocosh.divinefavor.common.util.UtilMap;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiblockTemplateData {
    private long[] blockPositionsSerialized;
    private int[] blockNameIndexes;
    private Map<Integer, String> blockIndexToNameMap;

    public MultiblockTemplateData(List<MultiblockBlockData> multiblockBlockData) {
        List<BlockPos> posList = new ArrayList<>(multiblockBlockData.size());
        List<String> names = new ArrayList<>(multiblockBlockData.size());

        for (MultiblockBlockData data : multiblockBlockData) {
            posList.add(data.pos);
            names.add(data.block.getRegistryName().toString());
        }

        this.blockPositionsSerialized = UtilBlockPos.serializePositions(posList);

        Map<String, Integer> blockNameToIndexMap = UtilMap.generateDistinctValueToIndexMap(names);
        this.blockIndexToNameMap = UtilMap.reverseMap(blockNameToIndexMap);
        this.blockNameIndexes = UtilMap.generateValueIndexArray(names,blockNameToIndexMap);
    }

    public List<MultiblockBlockData> getBlockDataList(){
        List<BlockPos> posList = UtilBlockPos.deserializePositions(blockPositionsSerialized);
        List<MultiblockBlockData> dataList = new ArrayList<>(blockPositionsSerialized.length);
        for (int i = 0; i < blockPositionsSerialized.length; i++) {
            int nameIndex = blockNameIndexes[i];
            String blockName = blockIndexToNameMap.get(nameIndex);
            Block block = Block.getBlockFromName(blockName);
            if(block == null)
                continue;
            BlockPos pos = posList.get(i);
            dataList.add(new MultiblockBlockData(pos,block));
        }
        return dataList;
    }
}
