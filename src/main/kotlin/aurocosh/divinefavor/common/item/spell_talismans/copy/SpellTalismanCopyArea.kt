package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.posField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class SpellTalismanCopyArea(name: String, spirit: ModSpirit, favorCost: Int) : SpellTalismanCopy(name, spirit, favorCost) {
    private val areaCorner = propertyHandler.registerEnumProperty("area_corner", defaultValue = AreaCorner.First, converter = AreaCorner)
    private val firstCorner = propertyHandler.registerBlockPosProperty("first_corner", BlockPos.ORIGIN)
    private val secondCorner = propertyHandler.registerBlockPosProperty("second_corner", BlockPos.ORIGIN)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: CastContext): Boolean {
        val (first, second) = context.stack.get(firstCorner, secondCorner)
        return isValidPoints(first, second)
    }

    override fun raycastBlock(stack: ItemStack, castType: CastType) = true

    override fun preProcess(context: CastContext): Boolean {
        if (!context.player.isSneaking) {
            val (stack, pos) = context.get(stackField, posField)

            val areaCorner = stack.get(areaCorner)
            val cornerToSet = if (areaCorner == AreaCorner.First) secondCorner else firstCorner
            stack.set(cornerToSet, pos)
            return false
        }
        return super.preProcess(context)
    }

    override fun validate(context: CastContext): Boolean {
        val (first, second) = context.stack.get(firstCorner, secondCorner)
        return isValidPoints(first, second) && super.validate(context)
    }

    private fun isValidPoints(first: BlockPos, second: BlockPos): Boolean {
        if (first == BlockPos.ORIGIN || second == BlockPos.ORIGIN)
            return false
        val cubeCoordinates = CuboidBoundingBox(first, second)
        val maxSize: Int = sequenceOf(cubeCoordinates.sizeX, cubeCoordinates.sizeY, cubeCoordinates.sizeZ).max() as Int
        if (maxSize > 125)
            return false
        return true
    }

    override fun getCoordinates(context: CastContext): CopyCoordinates {
        val (stack, world) = context.get(stackField, worldField)
        val (first, second) = stack.get(firstCorner, secondCorner)
        val boundingBox = CuboidBoundingBox(first, second)
        val coordinates = boundingBox.allPositionsInside.filterNot(world::isAirBlock).toList()
        return CopyCoordinates(coordinates, boundingBox)
    }
}
