package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

open class PositionPropertyWrapper(propertyHandler: StackPropertyHandler) {
    val isLockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)

    init {
        isLockPosition.addChangeListener { stack, value -> this.onPositionLock(stack, value) }
    }

    open fun shouldRaycastBlock(stack: ItemStack): Boolean = !stack.get(isLockPosition)
    open fun shouldRender(context: TalismanContext): Boolean = context.raycastValid || context.stack.get(isLockPosition)

    open fun getPosition(context: TalismanContext): BlockPos {
        return if (context.stack.get(isLockPosition)) context.stack.get(lockedPosition) else context.pos
    }

    open fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedPosition.setValue(stack, traceResult.blockPos, true)
    }
}