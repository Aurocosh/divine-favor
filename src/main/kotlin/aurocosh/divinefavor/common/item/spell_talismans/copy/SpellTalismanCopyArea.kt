package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.posField
import aurocosh.divinefavor.common.item.spell_talismans.context.stackField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class SpellTalismanCopyArea(name: String, spirit: ModSpirit, favorCost: Int) : SpellTalismanCopy(name, spirit, favorCost) {
    private val firstIsSet = propertyHandler.registerBoolProperty("first_is_set", defaultValue = false, showInTooltip = false, showInGui = false)
    private val firstCorner = propertyHandler.registerBlockPosProperty("first_corner", BlockPos.ORIGIN)
    private val secondCorner = propertyHandler.registerBlockPosProperty("second_corner", BlockPos.ORIGIN)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean {
        val (first, second) = context.stack.get(firstCorner, secondCorner)
        return isValidPoints(first, second)
    }

    override fun raycastBlock(stack: ItemStack, castType: CastType) = true

    override fun preProcess(context: TalismanContext): Boolean {
        if (context.player.isSneaking) {
            val (stack, pos) = context.get(stackField, posField)

            val firstSet = stack.get(firstIsSet)
            val cornerToSet = if (firstSet) secondCorner else firstCorner
            stack.set(cornerToSet, pos)
            stack.set(firstIsSet, !firstSet)
            return false
        }
        return super.preProcess(context)
    }

    override fun validate(context: TalismanContext): Boolean {
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

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (first, second) = context.stack.get(firstCorner, secondCorner)
        val cubeCoordinates = CuboidBoundingBox(first, second)
        return cubeCoordinates.allPositionsInside
    }
}
