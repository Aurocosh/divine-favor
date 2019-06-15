package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.client.block_ovelay.BlockConstructionRendering
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.talisman.properties.StackPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.StackPropertyInt
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*
import kotlin.collections.ArrayList

private data class RenderState(val pos: BlockPos, val count: Int)

class SpellTalismanBuildColumn(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 24)
//    private val lockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)

    override fun getFavorCost(itemStack: ItemStack): Int {
        return favorCost * blockCount.getValue(itemStack);
    }

    override fun validate(context: TalismanContext): Boolean {
        return super.validate(context)
    }

    override fun performActionServer(context: TalismanContext) {
        super.performActionServer(context) // TODO implement spell talisman functionality
    }

    @SideOnly(Side.CLIENT)
    override fun handleCustomRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val count = blockCount.getValue(context.stack)
        val coordinates = getCoordinates(context.pos, count)
        BlockConstructionRendering.render(lastEvent, context.player, Blocks.STONE.defaultState, coordinates)
    }

    private fun getCoordinates(blockPos: BlockPos, count: Int): List<BlockPos> {
        val renderState = RenderState(blockPos, count)
        if (renderState == lastRenderState)
            return coordinateCache

        coordinateCache.clear()
        var pos = blockPos
        for (i in 0 until count) {
            pos = pos.up()
            coordinateCache.add(pos)
        }
        return coordinateCache
    }

    companion object {
        private var lastRenderState = RenderState(BlockPos.ORIGIN, -1)
        private val coordinateCache: MutableList<BlockPos> = ArrayList()
    }
}
