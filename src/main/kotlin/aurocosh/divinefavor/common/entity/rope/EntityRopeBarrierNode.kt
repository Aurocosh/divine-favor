package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.common.ModRopes
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.world.World

class EntityRopeBarrierNode(world: World) : EntityRopeNodeBase(world) {
    private var durability: Int = 0

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeBarrierNode::class.java

    init {
        durability = ConfigRope.barrierRope.durability
    }

    override fun onEntityUpdate() {
        super.onEntityUpdate()

        if (world.isRemote)
            return

        val livingBases = world.getEntitiesWithinAABB(EntityLivingBase::class.java, AxisAlignedBB(position).grow(ConfigRope.barrierRope.repulsionRadius.toDouble()))
        val affectedMobs = livingBases.S.filter { livingBase -> livingBase !is EntityPlayer && livingBase.getDistanceSq(this) <= RADIUS_SQ }
        for (affectedMob in affectedMobs) {
            val direction = affectedMob.positionVector.subtract(this.positionVector)
            UtilEntity.addVelocity(affectedMob, direction, ConfigRope.barrierRope.repulsionForce)
            durability--
        }
        if (durability <= 0)
            setDead()
    }

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModRopes.rope_barrier, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModRopes.rope_barrier }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeBarrierNode(world)
    }

    companion object {
        private val RADIUS_SQ = ConfigRope.barrierRope.repulsionRadius * ConfigRope.barrierRope.repulsionRadius
    }
}