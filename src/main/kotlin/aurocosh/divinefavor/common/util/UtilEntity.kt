package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.multicatch
import com.google.common.base.Predicate
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.EntitySelectors
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler
import java.lang.reflect.InvocationTargetException
import java.util.*

object UtilEntity {
    fun setVelocity(entity: Entity, direction: Vec3d, velocity: Float) {
        val motion = direction.normalize().scale(velocity.toDouble())
        entity.motionX = motion.x
        entity.motionY = motion.y
        entity.motionZ = motion.z
    }

    fun addVelocity(entity: Entity, direction: Vec3d, velocity: Float) {
        val motion = direction.normalize().scale(velocity.toDouble())
        entity.motionX += motion.x
        entity.motionY += motion.y
        entity.motionZ += motion.z
    }

    fun tickLiquidWalk(livingBase: EntityLivingBase, liquid: Block) {
        val world = livingBase.entityWorld
        val pos = livingBase.position

        if (livingBase.isSneaking)
            return
        if (!world.isAirBlock(pos))
            return
        if (world.getBlock(pos.down()) !== liquid)
            return
        if (livingBase.motionY >= 0)
            return

        livingBase.motionY = 0.0
        livingBase.onGround = true
        livingBase.aiMoveSpeed = 0.1f
    }

    fun dropItemsOnGround(world: World, handler: IItemHandler?, pos: BlockPos) {
        if (handler == null)
            return
        for (i in 0 until handler.slots)
            dropItemOnGround(world, handler.getStackInSlot(i), pos)
    }

    fun dropItemOnGround(world: World, stack: ItemStack, pos: BlockPos) {
        if (!stack.isEmpty)
            world.spawnEntity(EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack))
    }

    fun toPlayerPosition(pos: BlockPos): Vec3d {
        return Vec3d(pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5)
        //        return new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
    }

    fun teleport(livingBase: EntityLivingBase, destination: GlobalBlockPos) {
        teleport(livingBase, destination.dimensionId, toPlayerPosition(destination.pos))
    }

    fun teleport(livingBase: EntityLivingBase, destination: BlockPos) {
        teleport(livingBase, livingBase.entityWorld.provider.dimension, toPlayerPosition(destination))
    }

    fun teleport(livingBase: EntityLivingBase, destination: Vec3d) {
        teleport(livingBase, livingBase.entityWorld.provider.dimension, destination)
    }

    fun teleport(livingBase: EntityLivingBase, dimension: Int, destination: BlockPos) {
        teleport(livingBase, dimension, toPlayerPosition(destination))
    }

    fun teleport(livingBase: EntityLivingBase, dimension: Int, destination: Vec3d) {
        val rotationYaw = livingBase.rotationYaw
        val rotationPitch = livingBase.rotationPitch

        val oldId = livingBase.entityWorld.provider.dimension
        if (oldId != dimension)
            teleportToDimension(livingBase, dimension, destination)

        livingBase.rotationYaw = rotationYaw
        livingBase.rotationPitch = rotationPitch
        livingBase.setPositionAndUpdate(destination.x, destination.y, destination.z)
    }

    fun teleportToDimension(livingBase: EntityLivingBase, dimension: Int, destination: Vec3d) {
        val server = livingBase.entityWorld.minecraftServer
        val worldServer = server!!.getWorld(dimension)

        if (livingBase is EntityPlayerMP) {
            livingBase.addExperienceLevel(0)
            server.playerList.transferPlayerToDimension(livingBase, dimension, CustomTeleporter(worldServer, destination))
        } else {
            val dimensionOld = livingBase.world.provider.dimension
            val oldWorldServer = server.getWorld(dimensionOld)
            server.playerList.transferEntityToWorld(livingBase, dimensionOld, oldWorldServer, worldServer, CustomTeleporter(worldServer, destination))
        }
        livingBase.setPositionAndUpdate(destination.x, destination.y, destination.z)
    }

    // client side only
    fun addVelocity(livingBase: EntityLivingBase, factor: Float) {
        var entity = livingBase
        if (entity.ridingEntity is EntityLivingBase)
            entity = entity.ridingEntity as EntityLivingBase
        if (entity.moveForward <= 0)
            return

        val extraVelocity = entity.lookVec.scale(factor.toDouble())
        entity.motionX += extraVelocity.x
        entity.motionY += extraVelocity.y
        entity.motionZ += extraVelocity.z
    }

    fun getBlockPlayerLookingAt(player: EntityPlayer, length: Double): RayTraceResult? {
        return UtilWorld.raycast(player.world, player.getPositionEyes(0f), player.lookVec, length)
    }

    fun <T : Entity> getNearbyEntities(clazz: Class<out T>, entity: Entity, radius: Double, filter: Predicate<in T>?): List<T> {
        val axis = entity.entityBoundingBox.grow(radius)
        return entity.world.getEntitiesWithinAABB(clazz, axis, filter)
    }

    fun isInCone(playerLookVec: Vec3d, origin: Vec3d, entity: Entity, coneTolerance: Double): Boolean {
        val vec = entity.positionVector.subtract(origin).normalize()
        return vec.dotProduct(playerLookVec) >= coneTolerance
    }

    fun <T : Entity?> getEntityPlayerLookingAt(player: EntityPlayer, clazz: Class<out T>, searchDistance: Double, ignoreMount: Boolean): T? {
        val entityLook = player.getLook(1f)
        val positionEyes = player.getPositionEyes(1f)
        val rayEnd = positionEyes.add(entityLook.x * searchDistance, entityLook.y * searchDistance, entityLook.z * searchDistance)

        val searchBoundingBox = player.entityBoundingBox.expand(entityLook.x * searchDistance, entityLook.y * searchDistance, entityLook.z * searchDistance).grow(1.0, 1.0, 1.0)
        val predicate = UtilPredicate.and<T?>(
                { EntitySelectors.NOT_SPECTATING.apply(it) },
                { it != null && it !== player && it.canBeCollidedWith() && (ignoreMount || it.lowestRidingEntity === player.lowestRidingEntity) })
        val entities = player.world.getEntitiesWithinAABB<T>(clazz, searchBoundingBox, predicate)

        var pointedEntity: T? = null
        var hitDistance = searchDistance
        for (entity in entities) {
            if (entity == null)
                continue
            val boundingBox = entity.entityBoundingBox.grow(entity.collisionBorderSize.toDouble())
            val rayTraceResult = boundingBox.calculateIntercept(positionEyes, rayEnd)

            if (boundingBox.contains(positionEyes)) {
                if (hitDistance >= 0.0) {
                    pointedEntity = entity
                    break
                }
            } else if (rayTraceResult != null) {
                val newHitDistance = positionEyes.distanceTo(rayTraceResult.hitVec)
                if (newHitDistance < hitDistance) {
                    pointedEntity = entity
                    hitDistance = newHitDistance
                }
            }
        }
        return pointedEntity
    }

    fun spawnEntity(world: World, spawnPos: BlockPos, clazz: Class<out Entity>): Boolean {
        return try {
            val constructor = clazz.getConstructor(World::class.java)
            val entity = constructor.newInstance(world)
            entity.setLocationAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)
            world.spawnEntity(entity)
            true
        } catch (e: Throwable) {
            e.multicatch(InvocationTargetException::class, NoSuchMethodException::class, InstantiationException::class, IllegalAccessException::class) {
                e.printStackTrace()
            }
            false
        }
    }

    fun <T : Entity> getEntityByUUID(world: World, clazz: Class<T>, alignedBB: AxisAlignedBB, uuid: UUID): T? {
        for (entity in world.getEntitiesWithinAABB(clazz, alignedBB))
            if (uuid == entity.uniqueID)
                return entity
        return null
    }
}
