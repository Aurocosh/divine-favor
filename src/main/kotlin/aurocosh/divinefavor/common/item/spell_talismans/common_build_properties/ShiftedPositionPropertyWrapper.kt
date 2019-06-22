package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.scale
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ShiftedPositionPropertyWrapper(propertyHandler: StackPropertyHandler) : PositionPropertyWrapper(propertyHandler) {
    val isRelative = propertyHandler.registerBoolProperty("is_relative", false)
    val shift: StackPropertyInt = propertyHandler.registerIntProperty("shift", 1, -8, 16)

    init {
        isRelative.addChangeListener(this::onRelativityChange)
    }

    override fun getPosition(context: TalismanContext): BlockPos {
        return when {
            context.stack.get(isRelative) -> getRelativePosition(context)
            else -> getShiftedPosition(context)
        }
    }

    override fun onPositionLock(stack: ItemStack, value: Boolean) {
        return when {
            stack.get(isRelative) -> onRelativePositionLock(stack, value)
            else -> onShiftedPositionLock(stack, value)
        }
    }

    private fun onRelativityChange(stack: ItemStack, value: Boolean) {
        val newShift = if (value) 6 else 1
        stack.set(shift, newShift, true)
    }

    private fun getShiftedPosition(context: TalismanContext): BlockPos {
        val blockPos = if (context.stack.get(isLockPosition)) context.stack.get(lockedPosition) else context.pos
        val shiftValue = context.stack.get(shift)
        val shiftVec = context.facing.directionVec.toBlockPos().scale(shiftValue)
        return blockPos.add(shiftVec)
    }

    private fun getRelativePosition(context: TalismanContext): BlockPos {
        val (player, stack) = context.getCommon()
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(shift)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val current = truncated.add(distanceVec).add(0, 2, 0)
        return if (stack.get(isLockPosition)) stack.get(lockedPosition) else current
    }

    private fun onShiftedPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedPosition.setValue(stack, traceResult.blockPos, true)
    }

    private fun onRelativePositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(shift)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val lockedPos = truncated.add(distanceVec).add(0, 2, 0)
        lockedPosition.setValue(stack, lockedPos, true)
    }

    override fun shouldRaycastBlock(stack: ItemStack): Boolean {
        return !stack.get(isRelative) && super.shouldRaycastBlock(stack)
    }

    override fun shouldRender(context: TalismanContext): Boolean {
        return context.stack.get(isRelative) || super.shouldRender(context)
    }
}