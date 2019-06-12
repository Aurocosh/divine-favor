package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.common.ModRopes
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityRopeGlowingNode(world: World) : EntityRopeNodeBase(world) {

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeGlowingNode::class.java

    override val isEmittingLight: Boolean
        get() = true

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModRopes.rope_glowing, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModRopes.rope_glowing }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeGlowingNode(world)
    }

    override fun isGlowing(): Boolean {
        return true
    }
}