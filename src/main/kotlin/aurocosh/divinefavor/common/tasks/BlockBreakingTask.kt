package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*

class BlockBreakingTask(blocksToRemove: List<BlockPos>, private val player: EntityPlayer, private val tool: ItemStack, private val blocksPerTick: Int) : ServerSideTask(player.world) {
    private val blocksToRemove: Queue<BlockPos>

    init {
        this.blocksToRemove = LinkedList(blocksToRemove)
    }

    @SubscribeEvent
    fun blockBreak(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var breakCount = Math.min(blocksPerTick, blocksToRemove.size)
        while (breakCount-- > 0)
            UtilBlock.removeBlock(player, world, tool, blocksToRemove.remove(), dropItem = true, isToolRequired = false, particles = false)
        if (blocksToRemove.isEmpty())
            finish()
    }
}