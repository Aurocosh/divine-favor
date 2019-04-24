package aurocosh.divinefavor.common.network.message.client.config;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.network.base.ConfigSyncClientMessage;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class MessageConfigOreBlocks extends ConfigSyncClientMessage {
    public ArrayList<String> oreBlocks;
//
//    public HashMap<Integer,String> testMap;
//
//    public Stack<Integer> oreBlocks;
//    public Vector<Integer> oreBlocks2;
//    public LinkedList<Integer> oreBlocks3;
//
//    public List<List<Integer>> testList;
//    public ArrayList<ArrayList<Integer>> testList2;

    public MessageConfigOreBlocks() {
    }

    @Override
    protected void getServerConfigs() {
        oreBlocks = new ArrayList<>();
        oreBlocks.addAll(Arrays.asList(ConfigGeneral.oreBlocks));

//        testMap = new HashMap<>();
//        testMap.put(1,"One");
//        testMap.put(2,"Two");
//        testMap.put(3,"Three");
//        testMap.put(4,"Four");
//        testMap.put(5,"Five");
//
//
//        testMap = new HashMap<>();
//        testMap.put(1,"One");
//        testMap.put(2,"Two");
//        testMap.put(3,"Three");
//        testMap.put(4,"Four");
//        testMap.put(5,"Five");
//
//        oreBlocks = new Stack<>();
//        oreBlocks.add(1);
//        oreBlocks.add(2);
//        oreBlocks.add(3);
//        oreBlocks2 = new Vector<>();
//        oreBlocks2.add(1);
//        oreBlocks2.add(2);
//        oreBlocks2.add(3);
//        oreBlocks3 = new LinkedList<>();
//        oreBlocks3.add(1);
//        oreBlocks3.add(2);
//        oreBlocks3.add(3);
//
//
//        ArrayList<Integer> sub1 = new ArrayList<>();
//        sub1.add(1);
//        sub1.add(1);
//        sub1.add(1);
//        sub1.add(2);
//        sub1.add(2);
//        sub1.add(2);
//
//        ArrayList<Integer> sub2 = new ArrayList<>();
//        ArrayList<Integer> sub3 = new ArrayList<>();
//        sub3.add(1);
//        sub3.add(2);
//        sub3.add(3);
//
//        testList = new ArrayList<>();
//        testList.add(sub1);
//        testList.add(sub2);
//        testList.add(sub3);
//
//        testList2 = new ArrayList<>();
//        testList2.add(sub1);
//        testList2.add(sub2);
//        testList2.add(sub3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        ConfigGeneral.ORE_BLOCKS.clear();
        for (String oreBlock : oreBlocks) {
            Block block = Block.getBlockFromName(oreBlock);
            if (block != null)
                ConfigGeneral.ORE_BLOCKS.add(block);
        }
    }
}
