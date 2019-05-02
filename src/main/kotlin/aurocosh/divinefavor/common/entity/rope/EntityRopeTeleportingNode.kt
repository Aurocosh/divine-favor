package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityRopeTeleportingNode(world: World) : EntityRopeNodeBase(world) {

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeTeleportingNode::class.java

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModItems.rope_teleporting, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModItems.rope_teleporting }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeTeleportingNode(world)
    }

    override fun canBeCollidedWith(): Boolean {
        return true
    }

    override fun canBePushed(): Boolean {
        return true
    }

    override fun applyEntityCollision(entityIn: Entity) {
        if (entityIn !is EntityLivingBase)
            return
        val lastConnectedNode = getLastConnectedNode(this)
        if (lastConnectedNode === this)
            return
        val placeToStand = UtilCoordinates.findPlaceToStand(lastConnectedNode.position, world, 8)
        UtilEntity.teleport(entityIn, placeToStand!!)
    }
}