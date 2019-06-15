package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModRopes
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityRopeInertNode(world: World) : EntityRopeNodeBase(world), IClimbable {

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeInertNode::class.java

    override val isMobile: Boolean
        get() = false

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModRopes.rope_inert, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModRopes.rope_inert }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeInertNode(world)
    }

    override fun getClimbingSpeed(): Float {
        return ConfigRope.inertRope.climbingSpeed
    }

    override fun canClimb(entity: Entity): Boolean {
        return entity.getDistanceSq(this) <= climbingDistanceSq
    }

    companion object {
        private val climbingDistanceSq = ConfigRope.inertRope.climbingDistance * ConfigRope.inertRope.climbingDistance
    }
}