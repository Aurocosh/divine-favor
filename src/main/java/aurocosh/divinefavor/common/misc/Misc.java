//package aurocosh.divinefavor.common.util.misc;
//
//import net.minecraft.util.math.BlockPos;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Misc {
//    private long[] blockPositionsSerialized;
//    private int[] blockNameIndexes;
//    private Map<Integer, String> blockIndexToNameMap;
//
//    public MultiblockTemplateData(List<BlockPos> blockPositions, List<String> blockRegestryNames) {
//        blockPositionsSerialized = new long[blockPositions.size()];
//        for (int i = 0; i < blockPositions.size(); i++)
//            blockPositionsSerialized[i] = blockPositions.get(i).toLong();
//
//        Map<String,Integer> blockNameToIndexMap = new HashMap<>();
//        int nextIndex = 0;
//        for (String name : blockRegestryNames)
//            if (!blockNameToIndexMap.containsKey(name))
//                blockNameToIndexMap.put(name, nextIndex++);
//
//        blockIndexToNameMap = new HashMap<>();
//        blockNameToIndexMap.forEach((name, index) -> blockIndexToNameMap.put(index,name));
//
//        blockNameIndexes = new int[blockPositions.size()];
//
//        for (int i = 0; i < blockRegestryNames.size(); i++) {
//            String name = blockRegestryNames.get(i);
//            blockNameIndexes[i] = blockNameToIndexMap.get(name);
//        }
//    }
//
//    public List<BlockPos> getPositions() {
//        List<BlockPos> posList = new ArrayList<>(blockPositionsSerialized.length);
//        for (int i = 0; i < blockPositionsSerialized.length; i++)
//            posList.add(BlockPos.fromLong(blockPositionsSerialized[i]));
//        return posList;
//    }
//
//    public List<>
//}
