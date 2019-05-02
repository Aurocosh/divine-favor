package aurocosh.divinefavor.common.network.message.client.config

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.network.base.ConfigSyncClientMessage
import net.minecraft.block.Block
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

import java.util.*

class MessageConfigOreBlocks : ConfigSyncClientMessage() {
    lateinit var oreBlocks: ArrayList<String>

//    lateinit var testMap: HashMap<Int, String>

//    lateinit var oreBlocks2: Vector<Int>
//    lateinit var oreBlocks3: LinkedList<Int>

//    lateinit var testList: MutableList<List<Int>>
//    lateinit var testList2: ArrayList<ArrayList<Int>>

    override fun getServerConfigs() {
        oreBlocks = ArrayList(Arrays.asList(*ConfigGeneral.oreBlocks))

//        testMap = HashMap()
//        testMap[1] = "One"
//        testMap[2] = "Two"
//        testMap[3] = "Three"
//        testMap[4] = "Four"
//        testMap[5] = "Five"
//
//
//        testMap = HashMap()
//        testMap[1] = "One"
//        testMap[2] = "Two"
//        testMap[3] = "Three"
//        testMap[4] = "Four"
//        testMap[5] = "Five"
//
//        oreBlocks2 = Vector()
//        oreBlocks2.add(1)
//        oreBlocks2.add(2)
//        oreBlocks2.add(3)
//        oreBlocks3 = LinkedList()
//        oreBlocks3.add(1)
//        oreBlocks3.add(2)
//        oreBlocks3.add(3)


//        val sub1 = ArrayList<Int>()
//        sub1.add(1)
//        sub1.add(1)
//        sub1.add(1)
//        sub1.add(2)
//        sub1.add(2)
//        sub1.add(2)
//
//        val sub2 = ArrayList<Int>()
//        val sub3 = ArrayList<Int>()
//        sub3.add(1)
//        sub3.add(2)
//        sub3.add(3)
//
//        testList = ArrayList()
//        testList.add(sub1)
//        testList.add(sub2)
//        testList.add(sub3)
//
//        testList2 = ArrayList()
//        testList2.add(sub1)
//        testList2.add(sub2)
//        testList2.add(sub3)
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        ConfigGeneral.ORE_BLOCKS.clear()
        for (oreBlock in oreBlocks) {
            val block = Block.getBlockFromName(oreBlock)
            if (block != null)
                ConfigGeneral.ORE_BLOCKS.add(block)
        }
    }
}
