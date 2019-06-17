package aurocosh.divinefavor.common.item.spell_talismans.common_build_properties

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.talisman.TalismanPropertyHandler
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

class RelativePositionPropertyWrapper(propertyHandler: TalismanPropertyHandler) {
    val distance: StackPropertyInt = propertyHandler.registerIntProperty("distance", 6, 1, 16)
    val isLockPosition: StackPropertyBool = propertyHandler.registerBoolProperty("lock_position", false)
    val lockedPosition: StackPropertyBlockPos = propertyHandler.registerBlockPosProperty("locked_position", BlockPos.ORIGIN)

    init {
        isLockPosition.addChangeListener(this::onPositionLock)
    }

    fun getPosition(context: TalismanContext): BlockPos {
        val (player, stack) = context.getCommon()
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(distance)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val current = truncated.add(distanceVec).add(0, 2, 0)
        val position = if (stack.get(isLockPosition)) stack.get(lockedPosition) else current
        return position
    }

    private fun onPositionLock(stack: ItemStack, value: Boolean) {
        val player = DivineFavor.proxy.clientPlayer
        val playerPos = player.positionVector
        val truncated = BlockPos(playerPos.x.toInt(), playerPos.y.toInt(), playerPos.z.toInt())
        val distanceValue = stack.get(distance)
        val distanceVec = player.lookVec.scale(distanceValue.toDouble()).toBlockPos()
        val lockedPos = truncated.add(distanceVec).add(0, 2, 0)
        lockedPosition.setValue(stack, lockedPos, true)
    }
}