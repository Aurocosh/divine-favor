package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*
import kotlin.math.min

open class BlockBuildingTask(blocksToPlace: List<BlockBuildData>, private val state: IBlockState, private val player: EntityPlayer, private val blocksPerTick: Int, private val breakBeforeBuild: Boolean = false) : ServerSideTask(player.world) {
    private val blocksToPlace: Queue<BlockBuildData> = LinkedList(blocksToPlace)

    @SubscribeEvent
    fun blockPlace(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var count = min(blocksPerTick, blocksToPlace.size)
        while (count-- > 0) {
            val (pos, isReal) = blocksToPlace.remove()
            if(breakBeforeBuild)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, pos, true, false,false)
            if (isReal)
                UtilBlock.replaceBlock(player, world, pos, state)
            else
                UtilBlock.replaceBlockWithGoo(player, world, pos, state)
        }
        if (blocksToPlace.isEmpty())
            finish()
    }
}