package aurocosh.divinefavor.common.muliblock;

import aurocosh.divinefavor.common.lib.math.Vector3i;
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

    public MultiblockTemplateData(List<MultiBlockPart> multiBlockData) {
        List<BlockPos> posList = new ArrayList<>(multiBlockData.size());
        List<String> names = new ArrayList<>(multiBlockData.size());

        for (MultiBlockPart data : multiBlockData) {
            posList.add(data.position.toBlockPos());
            names.add(data.block.getRegistryName().toString());
        }

        this.blockPositionsSerialized = UtilBlockPos.serializePositions(posList);

        Map<String, Integer> blockNameToIndexMap = UtilMap.generateDistinctValueToIndexMap(names);
        this.blockIndexToNameMap = UtilMap.reverseMap(blockNameToIndexMap);
        this.blockNameIndexes = UtilMap.generateValueIndexArray(names,blockNameToIndexMap);
    }

    public List<MultiBlockPart> getBlockDataList(){
        List<BlockPos> posList = UtilBlockPos.deserializePositions(blockPositionsSerialized);
        List<MultiBlockPart> dataList = new ArrayList<>(blockPositionsSerialized.length);
        for (int i = 0; i < blockPositionsSerialized.length; i++) {
            int nameIndex = blockNameIndexes[i];
            String blockName = blockIndexToNameMap.get(nameIndex);
            Block block = Block.getBlockFromName(blockName);
            if(block == null)
                continue;
            BlockPos pos = posList.get(i);
            dataList.add(new MultiBlockPart(Vector3i.convert(pos),block));
        }
        return dataList;
    }
}
