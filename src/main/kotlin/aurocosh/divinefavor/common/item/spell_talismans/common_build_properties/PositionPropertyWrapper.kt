package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.item.talisman.TalismanPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class PositionPropertyWrapper(propertyHandler: TalismanPropertyHandler) {
    val isLockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)

    init {
        isLockPosition.addChangeListener(this::onPositionLock)
    }

    fun getPosition(stack: ItemStack, current: BlockPos) = if (stack.get(isLockPosition)) stack.get(lockedPosition) else current

    fun validateCastType(context: TalismanContext): Boolean {
        if (context.castType == CastType.UseCast)
            return true
        if (context.castType == CastType.RightCast)
            return context.stack.get(isLockPosition) || context.valid
        return false
    }

    fun shouldRender(context: TalismanContext): Boolean {
        return context.stack.get(isLockPosition) || context.valid
    }

    fun preprocess(context: TalismanContext): Boolean {
        if (context.stack.get(isLockPosition))
            return true
        return context.valid
    }

    private fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedPosition.setValue(stack, traceResult.blockPos, true)
    }
}