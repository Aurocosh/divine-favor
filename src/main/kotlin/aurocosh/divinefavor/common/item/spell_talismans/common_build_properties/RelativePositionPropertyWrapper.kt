package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class RelativePositionPropertyWrapper(propertyHandler: StackPropertyHandler) : PositionPropertyWrapper(propertyHandler) {
    val distance: StackPropertyInt = propertyHandler.registerIntProperty("distance", 6, 1, 16)

    override fun shouldRaycastBlock(stack: ItemStack) = false
    override fun shouldRender(context: CastContext) = true

    override fun getPosition(context: CastContext): BlockPos {
        val (player, stack) = context.getCommon()
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(distance)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val current = truncated.add(distanceVec).add(0, 2, 0)
        return if (stack.get(isLockPosition)) stack.get(lockedPosition) else current
    }

    override fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(distance)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val lockedPos = truncated.add(distanceVec).add(0, 2, 0)
        lockedPosition.setValue(stack, lockedPos, true)
    }
}