package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyEnumFacing
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing

class RotationPropertyWrapper(propertyHandler: StackPropertyHandler) {
    val isLockRotation: StackPropertyBool = propertyHandler.registerBoolProperty("lock_rotation", false)
    val lockedRotation: StackPropertyEnumFacing = propertyHandler.registerEnumFacingProperty("locked_rotation", EnumFacing.UP)

    fun getRotation(stack: ItemStack, current: EnumFacing) = if (stack.get(isLockRotation)) stack.get(lockedRotation) else current

    init {
        isLockRotation.addChangeListener(this::onLock)
    }

    private fun onLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        lockedRotation.setValue(stack, player.horizontalFacing, true)
    }
}