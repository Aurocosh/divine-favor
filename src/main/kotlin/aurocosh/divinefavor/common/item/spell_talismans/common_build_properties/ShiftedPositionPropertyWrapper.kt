package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.talisman.TalismanPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.scale
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class ShiftedPositionPropertyWrapper(propertyHandler: TalismanPropertyHandler) {
    val isLockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)
    val shift: StackPropertyInt = propertyHandler.registerIntProperty("shift", 1, -8, 8)

    init {
        isLockPosition.addChangeListener(this::onPositionLock)
    }

    fun shouldRaycastBlock(stack: ItemStack): Boolean = !stack.get(isLockPosition)
    fun shouldRender(context: TalismanContext): Boolean = context.raycastValid || context.stack.get(isLockPosition)

    fun getPosition(context: TalismanContext, current: BlockPos): BlockPos {
        val blockPos = if (context.stack.get(isLockPosition)) context.stack.get(lockedPosition) else current
        val shiftValue = context.stack.get(shift)
        val shiftVec = context.facing.directionVec.toBlockPos().scale(shiftValue)
        return blockPos.add(shiftVec)
    }

    private fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedPosition.setValue(stack, traceResult.blockPos, true)
    }
}