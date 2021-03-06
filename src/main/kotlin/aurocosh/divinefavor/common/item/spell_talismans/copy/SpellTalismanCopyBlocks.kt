package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.coordinate_generators.generateFloodFillCoordinates
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.cached_container.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class SpellTalismanCopyBlocks(name: String, spirit: ModSpirit, favorCost: Int) : SpellTalismanCopy(name, spirit, favorCost) {
    val blockCount: StackPropertyInt = propertyHandler.registerIntProperty("block_count", 300, 1, 8000)
    val isFuzzy: StackPropertyBool = propertyHandler.registerBoolProperty("fuzzy", true)
    val doNotSelectBelow = propertyHandler.registerBoolProperty("do_not_select_below", true)
    val positionPropertyWrapper: PositionPropertyWrapper = PositionPropertyWrapper(propertyHandler)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: CastContext): Boolean = positionPropertyWrapper.shouldRender(context)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    fun getBlockCount(stack: ItemStack): Int = blockCount.getValue(stack)

    override fun getCoordinates(context: CastContext): CopyCoordinates {
        val (stack, world, player) = context.get(stackField, worldField, playerField)
        val (fuzzy, doNotSelect) = stack.get(isFuzzy, doNotSelectBelow)
        val count = getBlockCount(stack)
        val blockPos = positionPropertyWrapper.getPosition(context)

        val coordinates = cachedContainer.getValue(blockPos, count, fuzzy, doNotSelect) {
            val playerPos = player.position
            val posList = generateFloodFillCoordinates(blockPos, count, world, fuzzy) { pos, _ -> !doNotSelect || pos.y >= playerPos.y }
            posList.filterNot(world::isAirBlock).toList()
        }
        val boundingBox = CuboidBoundingBox.getBoundingBox(coordinates)
        return CopyCoordinates(coordinates, boundingBox)
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
