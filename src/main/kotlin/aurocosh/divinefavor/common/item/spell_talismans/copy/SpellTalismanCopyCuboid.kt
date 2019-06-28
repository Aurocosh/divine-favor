package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.coordinate_generators.generateCuboid
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.PositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.CachedContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class SpellTalismanCopyCuboid(name: String, spirit: ModSpirit, favorCost: Int) : SpellTalismanCopy(name, spirit, favorCost) {
    private val up: StackPropertyInt = propertyHandler.registerIntProperty("up", 1, 0, 50)
    private val down: StackPropertyInt = propertyHandler.registerIntProperty("down", 1, 0, 50)
    private val left: StackPropertyInt = propertyHandler.registerIntProperty("left", 1, 0, 50)
    private val right: StackPropertyInt = propertyHandler.registerIntProperty("right", 1, 0, 50)
    private val depth: StackPropertyInt = propertyHandler.registerIntProperty("depth", 3, 1, 50)
    val positionPropertyWrapper: PositionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    fun getBlockCount(stack: ItemStack): Int {
        val (left, right, up, down, depth) = stack.get(left, right, up, down, depth)
        val width = left + 1 + right
        val height = up + 1 + down
        return (width * height * depth)
    }

    override fun getCoordinates(context: TalismanContext): List<BlockPos> {
        val (left, right, up, down, depth) = context.stack.get(left, right, up, down, depth)
        val pos = positionPropertyWrapper.getPosition(context)
        val facing = context.facing

        return cachedContainer.getValue(facing, pos, up, down, left, right, depth) {
            val directions = UtilPlayer.getRelativeDirections(context.player, facing)
            generateCuboid(directions, pos, up, down, left, right, depth).toList()
        }
    }

    companion object {
        private val cachedContainer = CachedContainer { emptyList<BlockPos>() }
    }
}
