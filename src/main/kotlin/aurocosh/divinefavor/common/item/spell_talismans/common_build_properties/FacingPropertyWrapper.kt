package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyEnumFacing
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing

class FacingPropertyWrapper(propertyHandler: StackPropertyHandler) {
    val isLockFacing: StackPropertyBool = propertyHandler.registerBoolProperty("lock_facing", false)
    val lockedFacing: StackPropertyEnumFacing = propertyHandler.registerEnumFacingProperty("locked_facing", EnumFacing.UP)

    init {
        isLockFacing.addChangeListener(this::onFacingLock)
    }

    fun getFacing(stack: ItemStack, current: EnumFacing) = if (stack.get(isLockFacing)) stack.get(lockedFacing) else current

    private fun onFacingLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val traceResult = UtilEntity.getBlockPlayerLookingAt(player) ?: return
        lockedFacing.setValue(stack, traceResult.sideHit, true)
    }
}