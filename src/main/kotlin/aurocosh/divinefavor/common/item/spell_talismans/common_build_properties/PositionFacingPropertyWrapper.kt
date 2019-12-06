package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnumFacing
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing

open class PositionFacingPropertyWrapper(propertyHandler: StackPropertyHandler) : PositionPropertyWrapper(propertyHandler) {
    val lockedFacing: StackPropertyEnumFacing = propertyHandler.registerEnumFacingProperty("locked_facing", EnumFacing.UP)

    init {
        isLockPosition.addChangeListener { stack, value -> this.onFacingLock(stack, value) }
    }

    open fun getFacing(context: CastContext): EnumFacing {
        return if (context.stack.get(isLockPosition)) context.stack.get(lockedFacing) else context.facing
    }

    private fun onFacingLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedFacing.setValue(stack, traceResult.sideHit, true)
    }
}