package aurocosh.divinefavor.common.item.bathing_blend

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.tasks.BlockPlacingServerTask
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.lang.Integer.min
import kotlin.math.abs

class ItemBathingBlendEtherealGoo() : ItemBathingBlend("ethereal_goo", UtilTick.secondsToTicks(5f), 1) {
    override fun convertBlocks(world: World, heaterPos: BlockPos, coordinates: List<BlockPos>) {
        val state = ModBlocks.ethereal_goo.defaultState
        val toConvert = min(8, coordinates.size)
        val finalCoordinates = coordinates
                .shuffled()
                .sortedBy { if (it.y == heaterPos.y) Int.MAX_VALUE - abs(heaterPos.distanceSq(it)).toInt() else it.y }
                .take(toConvert)

        val time = UtilTick.secondsToTicks(4f)
        val blocksPerTick = if (time > finalCoordinates.size) 1 else finalCoordinates.size / time
        BlockPlacingServerTask(finalCoordinates, state, world, blocksPerTick).start()
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}