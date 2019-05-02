package aurocosh.divinefavor.common.entity.rope.base

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.MoverType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

abstract class EntityRopeNodeBase(world: World) : Entity(world) {

    private var canExtend = true
    private var pickUp = false
    private var despawnTimer = 0

    var nextNodeUUID: UUID? = null
        set(uuid) {
            field = uuid
            if (cachedNextNodeEntity != null && cachedNextNodeEntity!!.uniqueID != uuid)
                cachedNextNodeEntity = null
        }
    var previousNodeUUID: UUID? = null
        set(uuid) {
            field = uuid
            if (cachedPrevNodeEntity != null && cachedPrevNodeEntity!!.uniqueID != uuid)
                cachedPrevNodeEntity = null
        }

    private var cachedPrevNodeDW: Int = 0
    private var cachedNextNodeDW: Int = 0

    private var cachedNextNodeEntity: Entity? = null
    private var cachedPrevNodeEntity: Entity? = null

    private var lightBlock: BlockPos? = null

    val isAttached: Boolean
        get() = !world.getCollisionBoxes(this, entityBoundingBox.grow(0.1, 0.1, 0.1)).isEmpty()

    protected abstract val entityClass: Class<out EntityRopeNodeBase>

    protected open val isEmittingLight: Boolean
        get() = false

    protected open val isMobile: Boolean
        get() = true

    val connectionToNext: Vec3d?
        get() {
            val nextNode: Entity?
            if (world.isRemote)
                nextNode = nextNodeClient
            else
                nextNode = nextNodeByUUID
            return if (nextNode != null) Vec3d(nextNode.posX - posX, nextNode.posY - posY, nextNode.posZ - posZ) else null
        }

    val nextNodeByUUID: Entity?
        get() {
            if (cachedNextNodeEntity != null && cachedNextNodeEntity!!.isEntityAlive && cachedNextNodeEntity!!.uniqueID == this.nextNodeUUID)
                return cachedNextNodeEntity
            else {
                val uuid = this.nextNodeUUID
                val entity = if (uuid == null) null else getEntityByUUID(uuid)
                cachedNextNodeEntity = entity
                return entity
            }
        }

    val previousNodeByUUID: Entity?
        get() {
            if (cachedPrevNodeEntity != null && cachedPrevNodeEntity!!.isEntityAlive && cachedPrevNodeEntity!!.uniqueID == previousNodeUUID)
                return cachedPrevNodeEntity
            else {
                val uuid = previousNodeUUID
                val entity = if (uuid == null) null else getEntityByUUID(uuid)
                cachedPrevNodeEntity = entity
                return entity
            }
        }

    val prevNode: Entity?
        get() = if (world.isRemote) previousNodeClient else previousNodeServer

    var nextNode: Entity?
        get() = if (world.isRemote) nextNodeClient else nextNodeServer
        set(entity) {
            cachedNextNodeEntity = entity
            nextNodeUUID = entity?.uniqueID
        }

    val nextNodeClient: Entity?
        @SideOnly(Side.CLIENT)
        get() {
            if (cachedNextNodeEntity == null || !cachedNextNodeEntity!!.isEntityAlive || cachedNextNodeEntity!!.entityId != getDataManager().get(DW_NEXT_NODE)) {
                val entity = world.getEntityByID(getDataManager().get(DW_NEXT_NODE))
                cachedNextNodeEntity = entity
                return entity
            }
            return cachedNextNodeEntity
        }

    val previousNodeClient: Entity?
        @SideOnly(Side.CLIENT)
        get() {
            if (cachedPrevNodeEntity == null || !cachedPrevNodeEntity!!.isEntityAlive || cachedPrevNodeEntity!!.entityId != getDataManager().get(DW_PREV_NODE)) {
                val entity = world.getEntityByID(getDataManager().get(DW_PREV_NODE))
                cachedPrevNodeEntity = entity
                return entity
            }
            return cachedPrevNodeEntity
        }

    private val nextNodeServer: Entity?
        get() {
            val nextNode = nextNodeByUUID

            if (nextNode != null && nextNode.entityId != cachedNextNodeDW) {
                getDataManager().set(DW_NEXT_NODE, nextNode.entityId)
                cachedNextNodeDW = nextNode.entityId
            } else if (nextNode == null && cachedNextNodeDW != -1) {
                getDataManager().set(DW_NEXT_NODE, -1)
                cachedNextNodeDW = -1
            }
            return nextNode
        }

    private val previousNodeServer: Entity?
        get() {
            val prevNode = previousNodeByUUID
            if (prevNode != null && prevNode.entityId != cachedPrevNodeDW) {
                getDataManager().set(DW_PREV_NODE, prevNode.entityId)
                cachedPrevNodeDW = prevNode.entityId
            } else if (prevNode == null && cachedPrevNodeDW != -1) {
                getDataManager().set(DW_PREV_NODE, -1)
                cachedPrevNodeDW = -1
            }
            return prevNode
        }

    init {
        setSize(0.1f, 0.1f)
    }

    override fun entityInit() {
        getDataManager().register(DW_PREV_NODE, -1)
        cachedPrevNodeDW = -1
        getDataManager().register(DW_NEXT_NODE, -1)
        cachedNextNodeDW = -1
    }

    override fun readEntityFromNBT(nbt: NBTTagCompound) {
        nextNodeUUID = if (nbt.hasUniqueId(TAG_NEXT_NODE_UUID)) nbt.getUniqueId(TAG_NEXT_NODE_UUID) else null
        previousNodeUUID = if (nbt.hasUniqueId(TAG_PREVIOUS_NODE_UUID)) nbt.getUniqueId(TAG_PREVIOUS_NODE_UUID) else null
        pickUp = nbt.getBoolean(TAG_PICK_UP)
        canExtend = nbt.getBoolean(TAG_CAN_EXTEND)
        despawnTimer = nbt.getInteger(TAG_DESPAWN_TIMER)
        lightBlock = nbt.fallbackNull(TAG_LIGHT_BLOCK, NBTTagCompound::getBlockPos)
    }

    override fun writeEntityToNBT(nbt: NBTTagCompound) {
        if (nextNodeUUID != null)
            nbt.setUniqueId(TAG_NEXT_NODE_UUID, nextNodeUUID!!)
        if (previousNodeUUID != null)
            nbt.setUniqueId(TAG_PREVIOUS_NODE_UUID, previousNodeUUID!!)
        nbt.setBoolean(TAG_PICK_UP, pickUp)
        nbt.setBoolean(TAG_CAN_EXTEND, canExtend)
        nbt.setInteger(TAG_DESPAWN_TIMER, despawnTimer)
        nbt.setNullable(TAG_LIGHT_BLOCK, NBTTagCompound::setBlockPos, lightBlock)
    }

    override fun onEntityUpdate() {
        prevPosX = posX
        prevPosY = posY
        prevPosZ = posZ

        val mobile = isMobile
        var attached = isAttached
        if (mobile && !attached) {
            handleWaterMovement()
            move(MoverType.SELF, motionX, motionY, motionZ)
        }

        val prevAttached = attached
        attached = isAttached

        if (attached && !prevAttached)
            world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1f, 1.5f)

        val nextNode = nextNode
        if (!world.isRemote && nextNode is EntityPlayer) {
            val player = nextNode as EntityPlayer?
            pickUpThisNode(player!!)
            dropNewNode(player)
        }

        if (!isEntityAlive)
            return

        if (isEmittingLight)
            handleLightBlocks(attached)

        val prevNode = prevNode
        if (mobile)
            handleRopeMovement(attached, nextNode, prevNode)
        processDespawn(nextNode, prevNode)
    }

    private fun handleLightBlocks(attached: Boolean) {
        if (attached) {
            val pos = position
            if (lightBlock != null && lightBlock != pos)
                destroyLightSource()

            if (lightBlock == null && world.isAirBlock(pos)) {
                world.setBlockState(pos, ModBlocks.cavingRopeLight.defaultState)
                lightBlock = pos
            }
        } else if (lightBlock != null)
            destroyLightSource()
    }

    private fun destroyLightSource() {
        if (world.isBlockLoaded(lightBlock!!) && world.getBlockState(lightBlock!!).block === ModBlocks.cavingRopeLight)
            world.setBlockToAir(lightBlock!!)
        lightBlock = null
    }


    private fun processDespawn(nextNode: Entity?, prevNode: Entity?) {
        if (world.isRemote)
            return
        if (nextNode == null) {
            if (prevNode == null)
                onKillCommand()
            else {
                despawnTimer++
                if (despawnTimer >= 1200 * 20) {
                    if (prevNode != null && prevNode is EntityRopeNodeBase) {
                        val prevRopeNode = prevNode as EntityRopeNodeBase?
                        prevRopeNode!!.nextNode = null
                        prevRopeNode.despawn()
                    }
                    onKillCommand()
                }
            }
        } else
            despawnTimer = 0
    }

    private fun handleRopeMovement(attached: Boolean, nextNode: Entity?, prevNode: Entity?) {
        if (attached || onGround || inWater) {
            motionX = 0.0
            motionY = 0.0
            motionZ = 0.0
            return
        }

        motionY *= 0.88
        motionX *= 0.88
        motionZ *= 0.88

        var isFloating = false
        if (nextNode != null && getDistance(nextNode) >= ROPE_LENGTH) {
            val connection = connectionToNext
            if (connection != null) {
                var mx = connection.x * 0.02
                var my = connection.y * 0.02
                var mz = connection.z * 0.02
                val len = Math.sqrt(mx * mx + my * my + mz * mz)
                if (len > 0.5) {
                    mx /= len * 0.5
                    my /= len * 0.5
                    mz /= len * 0.5
                }
                if (prevNode != null && prevNode.getDistance(posX + mx, posY + my, posZ + mz) < ROPE_LENGTH + 1) {
                    motionX += mx
                    motionZ += mz
                    motionY += my
                    isFloating = true
                }
            }
        }

        if (!isFloating)
            motionY -= 0.28

        if (nextNode != null) {
            val mx = motionX
            val my = motionY
            val mz = motionZ
            val nextPoint = Vec3d(posX + mx, posY + my, posZ + mz)
            val tetherPoint = Vec3d(nextNode.posX, nextNode.posY, nextNode.posZ)
            if (tetherPoint.distanceTo(nextPoint) >= ROPE_LENGTH) {
                var constrainedPoint = nextPoint.subtract(tetherPoint).normalize()
                constrainedPoint = Vec3d(
                        constrainedPoint.x * ROPE_LENGTH,
                        constrainedPoint.y * ROPE_LENGTH,
                        constrainedPoint.z * ROPE_LENGTH).add(tetherPoint.x, tetherPoint.y, tetherPoint.z)
                val diff = Vec3d(posX, posY, posZ).subtract(constrainedPoint)
                motionX = -diff.x
                motionY = -diff.y
                motionZ = -diff.z
            }
        }

        if (prevNode != null) {
            val mx = motionX
            val my = motionY
            val mz = motionZ
            val nextPoint = Vec3d(posX + mx, posY + my, posZ + mz)
            val tetherPoint = Vec3d(prevNode.posX, prevNode.posY, prevNode.posZ)
            if (tetherPoint.distanceTo(nextPoint) >= ROPE_LENGTH) {
                var constrainedPoint = nextPoint.subtract(tetherPoint).normalize()
                constrainedPoint = Vec3d(
                        constrainedPoint.x * ROPE_LENGTH,
                        constrainedPoint.y * ROPE_LENGTH,
                        constrainedPoint.z * ROPE_LENGTH).add(tetherPoint.x, tetherPoint.y, tetherPoint.z)
                val diff = Vec3d(posX, posY, posZ).subtract(constrainedPoint).scale(0.8)
                motionX = -diff.x
                motionY = -diff.y
                motionZ = -diff.z
            }
        }

        val speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ)
        motionX *= Math.min(speed, 0.05) / 0.05
        motionY *= Math.min(speed, 0.05) / 0.05
        motionZ *= Math.min(speed, 0.05) / 0.05
    }

    private fun pickUpThisNode(player: EntityPlayer) {
        val nodeDistance = player.getDistance(this)
        if (!pickUp)
            pickUp = nodeDistance > 1.5f
        else if (player.entityBoundingBox.grow(0.4, 0.4, 0.4).intersects(entityBoundingBox)) {
            removeNode(player)
            registerPickUp(player)
        }
    }

    protected open fun registerPickUp(player: EntityPlayer) {}

    private fun dropNewNode(player: EntityPlayer) {
        val nodeDistance = player.getDistance(this)
        if (nodeDistance < ROPE_LENGTH - 1)
            canExtend = true
        if (canExtend && nodeDistance > ROPE_LENGTH + 1) {
            if (canDropNewNode(player)) {
                val connection = connectionToNext
                if (connection != null) {
                    val playerPosition = player.positionVector
                    var newPos = playerPosition.add(connection.scale(-0.5)).add(0.0, 0.1, 0.0)
                    val result = world.rayTraceBlocks(playerPosition, newPos, false)
                    if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && result.hitVec.squareDistanceTo(playerPosition) < newPos.squareDistanceTo(playerPosition))
                        newPos = result.hitVec.add(result.hitVec.subtract(positionVector).normalize().scale(0.1))
                    extendRope(player, newPos.x, newPos.y, newPos.z)
                }
            }
        }
        if (nodeDistance > ROPE_LENGTH_MAX) {
            player.sendStatusMessage(TextComponentTranslation("chat.rope.disconnected"), true)
            nextNode = null
        }
    }

    protected open fun canDropNewNode(player: EntityPlayer): Boolean {
        return true
    }

    override fun processInitialInteract(player: EntityPlayer, hand: EnumHand): Boolean {
        if (world.isRemote)
            return true

        val prevNode = previousNodeByUUID
        val nextNode = nextNodeByUUID
        val entityClass = entityClass

        if (prevNode != null) {
            if (nextNode == null) {
                val connectedRopeNode = player.world.loadedEntityList.S.filterIsInstance(entityClass).firstOrNull { node -> node.nextNodeByUUID === player }
                if (connectedRopeNode != null) {
                    player.sendStatusMessage(TextComponentTranslation("chat.rope.already_connected"), true)
                    return false
                }

                this.nextNode = player
                return true
            } else if (!entityClass.isInstance(nextNode)) {
                this.nextNode = null
                return true
            }
        }

        if (entityClass.isInstance(nextNode)) {
            val endNode = getLastConnectedNode((nextNode as EntityRopeNodeBase?)!!)
            if (endNode.nextNodeByUUID == null && entityClass.isInstance(endNode.previousNodeByUUID)) {
                (endNode.previousNodeByUUID as EntityRopeNodeBase).nextNode = null
                endNode.setDead()

                registerPickUp(player)
                return true
            }
        }
        return false
    }

    fun getLastConnectedNode(endNode: EntityRopeNodeBase): EntityRopeNodeBase {
        var endNode = endNode
        val clazz = entityClass
        while (clazz.isInstance(endNode.nextNodeByUUID) && endNode.nextNodeByUUID !== this)
            endNode = endNode.nextNodeByUUID as EntityRopeNodeBase
        return endNode
    }

    override fun canBeCollidedWith(): Boolean {
        return true
    }

    override fun setDead() {
        super.setDead()
        if (lightBlock != null)
            destroyLightSource()
    }

    @SideOnly(Side.CLIENT)
    override fun isInRangeToRenderDist(distance: Double): Boolean {
        return distance < 1024.0
    }

    fun removeNode(nextConnectionNode: Entity) {
        val prevNode = previousNodeByUUID
        if (prevNode is EntityRopeNodeBase) {
            val node = prevNode as EntityRopeNodeBase?
            node!!.nextNode = nextConnectionNode
            node.canExtend = false
        }
        onKillCommand()
    }

    fun despawn() {
        despawnTimer = 1200 * 20
    }

    protected abstract fun makeNewNode(world: World): EntityRopeNodeBase

    fun extendRope(entity: Entity, x: Double, y: Double, z: Double) {
        val ropeNode = makeNewNode(world)
        ropeNode.setLocationAndAngles(x, y, z, 0f, 0f)
        ropeNode.setPreviousNode(this)
        ropeNode.nextNode = entity
        nextNode = ropeNode
        world.spawnEntity(ropeNode)
        if (ropeNode.isAttached)
            world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1f, 1.5f)
    }

    fun setPreviousNode(entity: Entity?) {
        cachedPrevNodeEntity = entity
        previousNodeUUID = entity?.uniqueID
    }

    private fun getEntityByUUID(uuid: UUID): Entity? {
        val alignedBB = entityBoundingBox.grow(24.0, 24.0, 24.0)
        return UtilEntity.getEntityByUUID(world, Entity::class.java, alignedBB, uuid)
    }

    companion object {
        private val DW_PREV_NODE = EntityDataManager.createKey(EntityRopeNodeBase::class.java, DataSerializers.VARINT)
        private val DW_NEXT_NODE = EntityDataManager.createKey(EntityRopeNodeBase::class.java, DataSerializers.VARINT)

        val ROPE_LENGTH = 4.0
        val ROPE_LENGTH_SQ = EntityRopeNodeBase.ROPE_LENGTH * EntityRopeNodeBase.ROPE_LENGTH

        val ROPE_LENGTH_MAX = 12.0
        private val TAG_NEXT_NODE_UUID = "nextNodeUUID"
        private val TAG_PREVIOUS_NODE_UUID = "previousNodeUUID"
        private val TAG_PICK_UP = "pickUp"
        private val TAG_CAN_EXTEND = "canExtend"
        private val TAG_DESPAWN_TIMER = "despawnTimer"
        private val TAG_LIGHT_BLOCK = "lightBlock"
    }
}