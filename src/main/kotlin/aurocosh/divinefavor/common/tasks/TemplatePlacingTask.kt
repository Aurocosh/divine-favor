package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.block_templates.TemplateFinalBlockState
import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*
import kotlin.math.min

open class TemplatePlacingTask(template: List<TemplateFinalBlockState>, private val player: EntityPlayer, private val blocksPerTick: Int) : ServerSideTask(player.world) {
    private val blocksToPlace: Queue<TemplateFinalBlockState> = LinkedList(template)

    @SubscribeEvent
    fun blockPlace(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var count = min(blocksPerTick, blocksToPlace.size)
        while (count-- > 0)
            buildBlock(player, world, blocksToPlace.remove())
        if (blocksToPlace.isEmpty())
            finish()
    }

    private fun buildBlock(player: EntityPlayer, world: World, blockState: TemplateFinalBlockState) {
        val drops = NonNullList.create<ItemStack>()
        blockState.state.block.getDrops(drops, world, blockState.pos, blockState.state, 0)

        val stack = blockState.metaItem.toStack()
        val dropCount = drops.filter { it.item == stack.item }.count()
        val itemsToConsume = if (dropCount > 0) dropCount else 1

        if (UtilPlayer.consumeItems(stack, player, itemsToConsume))
            UtilBlock.replaceBlock(player, world, blockState.pos, blockState.state)
    }
}