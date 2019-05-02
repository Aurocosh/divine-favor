package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityRopeGuideNode(world: World) : EntityRopeNodeBase(world), IClimbable {

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeGuideNode::class.java

    override val isEmittingLight: Boolean
        get() = true

    override val isMobile: Boolean
        get() = true

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModItems.rope_guide, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModItems.rope_guide }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeGuideNode(world)
    }

    override fun getClimbingSpeed(): Float {
        return ConfigRope.guideRope.climbingSpeed
    }

    override fun canClimb(entityIn: Entity): Boolean {
        return entityIn.getDistanceSq(this) <= climbingDistanceSq
    }

    companion object {
        private val climbingDistanceSq = ConfigRope.guideRope.climbingDistance * ConfigRope.guideRope.climbingDistance
    }
}