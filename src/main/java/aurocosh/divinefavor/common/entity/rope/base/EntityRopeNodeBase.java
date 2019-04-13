package aurocosh.divinefavor.common.entity.rope.base;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public abstract class EntityRopeNodeBase extends Entity {
    private static final DataParameter<Integer> DW_PREV_NODE = EntityDataManager.createKey(EntityRopeNodeBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_NEXT_NODE = EntityDataManager.createKey(EntityRopeNodeBase.class, DataSerializers.VARINT);

    public static final double ROPE_LENGTH = 4.0D;
    public static final double ROPE_LENGTH_SQ = EntityRopeNodeBase.ROPE_LENGTH * EntityRopeNodeBase.ROPE_LENGTH;

    public static final double ROPE_LENGTH_MAX = 12.0D;
    private static final String TAG_NEXT_NODE_UUID = "nextNodeUUID";
    private static final String TAG_PREVIOUS_NODE_UUID = "previousNodeUUID";
    private static final String TAG_PICK_UP = "pickUp";
    private static final String TAG_CAN_EXTEND = "canExtend";
    private static final String TAG_DESPAWN_TIMER = "despawnTimer";
    private static final String TAG_LIGHT_BLOCK = "lightBlock";

    private boolean canExtend = true;
    private boolean pickUp = false;
    private int despawnTimer = 0;

    private UUID nextNodeUUID;
    private UUID prevNodeUUID;

    private int cachedPrevNodeDW;
    private int cachedNextNodeDW;

    private Entity cachedNextNodeEntity;
    private Entity cachedPrevNodeEntity;

    private BlockPos lightBlock = null;

    public EntityRopeNodeBase(World world) {
        super(world);
        setSize(0.1f, 0.1f);
    }

    @Override
    protected void entityInit() {
        getDataManager().register(DW_PREV_NODE, -1);
        cachedPrevNodeDW = -1;
        getDataManager().register(DW_NEXT_NODE, -1);
        cachedNextNodeDW = -1;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setNextNodeUUID(nbt.hasUniqueId(TAG_NEXT_NODE_UUID) ? nbt.getUniqueId(TAG_NEXT_NODE_UUID) : null);
        setPreviousNodeUUID(nbt.hasUniqueId(TAG_PREVIOUS_NODE_UUID) ? nbt.getUniqueId(TAG_PREVIOUS_NODE_UUID) : null);
        pickUp = nbt.getBoolean(TAG_PICK_UP);
        canExtend = nbt.getBoolean(TAG_CAN_EXTEND);
        despawnTimer = nbt.getInteger(TAG_DESPAWN_TIMER);
        lightBlock = UtilNbt.getBlockPos(nbt, TAG_LIGHT_BLOCK, null);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        if (getNextNodeUUID() != null)
            nbt.setUniqueId(TAG_NEXT_NODE_UUID, getNextNodeUUID());
        if (getPreviousNodeUUID() != null)
            nbt.setUniqueId(TAG_PREVIOUS_NODE_UUID, getPreviousNodeUUID());
        nbt.setBoolean(TAG_PICK_UP, pickUp);
        nbt.setBoolean(TAG_CAN_EXTEND, canExtend);
        nbt.setInteger(TAG_DESPAWN_TIMER, despawnTimer);
        UtilNbt.setBlockPos(nbt, TAG_LIGHT_BLOCK, lightBlock);
    }

    @Override
    public void onEntityUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        boolean mobile = isMobile();
        boolean attached = isAttached();
        if (mobile && !attached) {
            handleWaterMovement();
            move(MoverType.SELF, motionX, motionY, motionZ);
        }

        boolean prevAttached = attached;
        attached = isAttached();

        if (attached && !prevAttached)
            world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1, 1.5f);

        Entity nextNode = getNextNode();
        if (!world.isRemote && nextNode instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) nextNode;
            pickUpThisNode(player);
            dropNewNode(player);
        }

        if (!isEntityAlive())
            return;

        if (isEmittingLight())
            handleLightBlocks(attached);

        Entity prevNode = getPrevNode();
        if(mobile)
            handleRopeMovement(attached, nextNode, prevNode);
        processDespawn(nextNode, prevNode);
    }

    private void handleLightBlocks(boolean attached) {
        if (attached) {
            BlockPos pos = getPosition();
            if (lightBlock != null && !lightBlock.equals(pos))
                destroyLightSource();

            if (lightBlock == null && world.isAirBlock(pos)) {
                world.setBlockState(pos, ModBlocks.cavingRopeLight.getDefaultState());
                lightBlock = pos;
            }
        }
        else if (lightBlock != null)
            destroyLightSource();
    }

    private void destroyLightSource() {
        if (world.isBlockLoaded(lightBlock) && world.getBlockState(lightBlock).getBlock() == ModBlocks.cavingRopeLight)
            world.setBlockToAir(lightBlock);
        lightBlock = null;
    }


    private void processDespawn(Entity nextNode, Entity prevNode) {
        if (world.isRemote)
            return;
        if (nextNode == null) {
            if (prevNode == null)
                onKillCommand();
            else {
                despawnTimer++;
                if (despawnTimer >= 1200 * 20) {
                    if (prevNode != null && prevNode instanceof EntityRopeNodeBase) {
                        EntityRopeNodeBase prevRopeNode = (EntityRopeNodeBase) prevNode;
                        prevRopeNode.setNextNode(null);
                        prevRopeNode.despawn();
                    }
                    onKillCommand();
                }
            }
        }
        else
            despawnTimer = 0;
    }

    private void handleRopeMovement(boolean attached, Entity nextNode, Entity prevNode) {
        if (attached || onGround || inWater) {
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
            return;
        }

        motionY *= 0.88D;
        motionX *= 0.88D;
        motionZ *= 0.88D;

        boolean isFloating = false;
        if (nextNode != null && getDistance(nextNode) >= ROPE_LENGTH) {
            Vec3d connection = getConnectionToNext();
            if (connection != null) {
                double mx = connection.x * 0.02D;
                double my = connection.y * 0.02D;
                double mz = connection.z * 0.02D;
                double len = Math.sqrt(mx * mx + my * my + mz * mz);
                if (len > 0.5D) {
                    mx /= len * 0.5D;
                    my /= len * 0.5D;
                    mz /= len * 0.5D;
                }
                if (prevNode != null && prevNode.getDistance(posX + mx, posY + my, posZ + mz) < ROPE_LENGTH + 1) {
                    motionX += mx;
                    motionZ += mz;
                    motionY += my;
                    isFloating = true;
                }
            }
        }

        if (!isFloating)
            motionY -= 0.28D;

        if (nextNode != null) {
            double mx = motionX;
            double my = motionY;
            double mz = motionZ;
            Vec3d nextPoint = new Vec3d(posX + mx, posY + my, posZ + mz);
            Vec3d tetherPoint = new Vec3d(nextNode.posX, nextNode.posY, nextNode.posZ);
            if (tetherPoint.distanceTo(nextPoint) >= ROPE_LENGTH) {
                Vec3d constrainedPoint = nextPoint.subtract(tetherPoint).normalize();
                constrainedPoint = new Vec3d(
                        constrainedPoint.x * ROPE_LENGTH,
                        constrainedPoint.y * ROPE_LENGTH,
                        constrainedPoint.z * ROPE_LENGTH).add(tetherPoint.x, tetherPoint.y, tetherPoint.z);
                Vec3d diff = new Vec3d(posX, posY, posZ).subtract(constrainedPoint);
                motionX = -diff.x;
                motionY = -diff.y;
                motionZ = -diff.z;
            }
        }

        if (prevNode != null) {
            double mx = motionX;
            double my = motionY;
            double mz = motionZ;
            Vec3d nextPoint = new Vec3d(posX + mx, posY + my, posZ + mz);
            Vec3d tetherPoint = new Vec3d(prevNode.posX, prevNode.posY, prevNode.posZ);
            if (tetherPoint.distanceTo(nextPoint) >= ROPE_LENGTH) {
                Vec3d constrainedPoint = nextPoint.subtract(tetherPoint).normalize();
                constrainedPoint = new Vec3d(
                        constrainedPoint.x * ROPE_LENGTH,
                        constrainedPoint.y * ROPE_LENGTH,
                        constrainedPoint.z * ROPE_LENGTH).add(tetherPoint.x, tetherPoint.y, tetherPoint.z);
                Vec3d diff = new Vec3d(posX, posY, posZ).subtract(constrainedPoint).scale(0.8D);
                motionX = -diff.x;
                motionY = -diff.y;
                motionZ = -diff.z;
            }
        }

        double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX *= Math.min(speed, 0.05D) / 0.05D;
        motionY *= Math.min(speed, 0.05D) / 0.05D;
        motionZ *= Math.min(speed, 0.05D) / 0.05D;
    }

    private void pickUpThisNode(final EntityPlayer player) {
        final float nodeDistance = player.getDistance(this);
        if (!pickUp)
            pickUp = nodeDistance > 1.5f;
        else if (player.getEntityBoundingBox().grow(0.4D, 0.4D, 0.4D).intersects(getEntityBoundingBox())) {
            removeNode(player);
            registerPickUp(player);
        }
    }

    protected void registerPickUp(EntityPlayer player) {
    }

    private void dropNewNode(final EntityPlayer player) {
        final float nodeDistance = player.getDistance(this);
        if (nodeDistance < ROPE_LENGTH - 1)
            canExtend = true;
        if (canExtend && nodeDistance > ROPE_LENGTH + 1) {
            if (canDropNewNode(player)) {
                Vec3d connection = getConnectionToNext();
                if (connection != null) {
                    Vec3d playerPosition = player.getPositionVector();
                    Vec3d newPos = playerPosition.add(connection.scale(-0.5D)).add(0, 0.1D, 0);
                    RayTraceResult result = world.rayTraceBlocks(playerPosition, newPos, false);
                    if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && result.hitVec.squareDistanceTo(playerPosition) < newPos.squareDistanceTo(playerPosition))
                        newPos = result.hitVec.add(result.hitVec.subtract(getPositionVector()).normalize().scale(0.1D));
                    extendRope(player, newPos.x, newPos.y, newPos.z);
                }
            }
        }
        if (nodeDistance > ROPE_LENGTH_MAX) {
            player.sendStatusMessage(new TextComponentTranslation("chat.rope.disconnected"), true);
            setNextNode(null);
        }
    }

    protected boolean canDropNewNode(final EntityPlayer player) {
        return true;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
            return true;
        else {
            Entity prevNode = getPreviousNodeByUUID();
            Entity nextNode = getNextNodeByUUID();
            Class<? extends EntityRopeNodeBase> entityClass = getEntityClass();

            if (prevNode != null) {
                if (nextNode == null) {
                    EntityRopeNodeBase connectedRopeNode = (EntityRopeNodeBase) UtilList.findFirst(player.world.loadedEntityList, obj -> entityClass.isInstance(obj) && ((EntityRopeNodeBase) obj).getNextNodeByUUID() == player);
                    if (connectedRopeNode != null) {
                        player.sendStatusMessage(new TextComponentTranslation("chat.rope.already_connected"), true);
                        return false;
                    }

                    setNextNode(player);
                    return true;
                }
                else if (!entityClass.isInstance(nextNode)) {
                    setNextNode(null);
                    return true;
                }
            }

            if (entityClass.isInstance(nextNode)) {
                EntityRopeNodeBase endNode = getLastConnectedNode((EntityRopeNodeBase) nextNode);
                if (endNode.getNextNodeByUUID() == null && entityClass.isInstance(endNode.getPreviousNodeByUUID())) {
                    ((EntityRopeNodeBase) endNode.getPreviousNodeByUUID()).setNextNode(null);
                    endNode.setDead();

                    registerPickUp(player);
                    return true;
                }
            }
        }
        return false;
    }

    public EntityRopeNodeBase getLastConnectedNode(EntityRopeNodeBase endNode) {
        Class<? extends EntityRopeNodeBase> clazz = getEntityClass();
        while (clazz.isInstance(endNode.getNextNodeByUUID()) && endNode.getNextNodeByUUID() != this)
            endNode = (EntityRopeNodeBase) endNode.getNextNodeByUUID();
        return endNode;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void setDead() {
        super.setDead();
        if (lightBlock != null)
            destroyLightSource();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 1024.0D;
    }

    public void removeNode(Entity nextConnectionNode) {
        Entity prevNode = getPreviousNodeByUUID();
        if (prevNode instanceof EntityRopeNodeBase) {
            EntityRopeNodeBase node = (EntityRopeNodeBase) prevNode;
            node.setNextNode(nextConnectionNode);
            node.canExtend = false;
        }
        onKillCommand();
    }

    public void despawn() {
        despawnTimer = 1200 * 20;
    }

    public boolean isAttached() {
        return !world.getCollisionBoxes(this, getEntityBoundingBox().grow(0.1D, 0.1D, 0.1D)).isEmpty();
    }

    protected abstract EntityRopeNodeBase makeNewNode(World world);

    protected abstract Class<? extends EntityRopeNodeBase> getEntityClass();

    protected boolean isEmittingLight() {
        return false;
    }

    protected boolean isMobile() {
        return true;
    }

    public void extendRope(Entity entity, double x, double y, double z) {
        EntityRopeNodeBase ropeNode = makeNewNode(world);
        ropeNode.setLocationAndAngles(x, y, z, 0, 0);
        ropeNode.setPreviousNode(this);
        ropeNode.setNextNode(entity);
        setNextNode(ropeNode);
        world.spawnEntity(ropeNode);
        if (ropeNode.isAttached())
            world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_METAL_STEP, SoundCategory.PLAYERS, 1, 1.5F);
    }

    public Vec3d getConnectionToNext() {
        Entity nextNode;
        if (world.isRemote)
            nextNode = getNextNodeClient();
        else
            nextNode = getNextNodeByUUID();
        if (nextNode != null)
            return new Vec3d(nextNode.posX - posX, nextNode.posY - posY, nextNode.posZ - posZ);
        return null;
    }

    public void setNextNodeUUID(UUID uuid) {
        nextNodeUUID = uuid;
        if (cachedNextNodeEntity != null && !cachedNextNodeEntity.getUniqueID().equals(uuid))
            cachedNextNodeEntity = null;
    }

    public UUID getNextNodeUUID() {
        return nextNodeUUID;
    }

    public void setNextNode(Entity entity) {
        cachedNextNodeEntity = entity;
        setNextNodeUUID(entity == null ? null : entity.getUniqueID());
    }

    public Entity getNextNodeByUUID() {
        if (cachedNextNodeEntity != null && cachedNextNodeEntity.isEntityAlive() && cachedNextNodeEntity.getUniqueID().equals(nextNodeUUID))
            return cachedNextNodeEntity;
        else {
            UUID uuid = nextNodeUUID;
            Entity entity = uuid == null ? null : getEntityByUUID(uuid);
            cachedNextNodeEntity = entity;
            return entity;
        }
    }

    public void setPreviousNodeUUID(UUID uuid) {
        prevNodeUUID = uuid;
        if (cachedPrevNodeEntity != null && !cachedPrevNodeEntity.getUniqueID().equals(uuid))
            cachedPrevNodeEntity = null;
    }

    public UUID getPreviousNodeUUID() {
        return prevNodeUUID;
    }

    public void setPreviousNode(Entity entity) {
        cachedPrevNodeEntity = entity;
        setPreviousNodeUUID(entity == null ? null : entity.getUniqueID());
    }

    public Entity getPreviousNodeByUUID() {
        if (cachedPrevNodeEntity != null && cachedPrevNodeEntity.isEntityAlive() && cachedPrevNodeEntity.getUniqueID().equals(prevNodeUUID))
            return cachedPrevNodeEntity;
        else {
            UUID uuid = prevNodeUUID;
            Entity entity = uuid == null ? null : getEntityByUUID(uuid);
            cachedPrevNodeEntity = entity;
            return entity;
        }
    }

    public Entity getPrevNode() {
        return world.isRemote ? getPreviousNodeClient() : getPreviousNodeServer();
    }

    public Entity getNextNode() {
        return world.isRemote ? getNextNodeClient() : getNextNodeServer();
    }

    @SideOnly(Side.CLIENT)
    public Entity getNextNodeClient() {
        if (cachedNextNodeEntity == null || !cachedNextNodeEntity.isEntityAlive() || cachedNextNodeEntity.getEntityId() != getDataManager().get(DW_NEXT_NODE)) {
            Entity entity = world.getEntityByID(getDataManager().get(DW_NEXT_NODE));
            cachedNextNodeEntity = entity;
            return entity;
        }
        return cachedNextNodeEntity;
    }

    @SideOnly(Side.CLIENT)
    public Entity getPreviousNodeClient() {
        if (cachedPrevNodeEntity == null || !cachedPrevNodeEntity.isEntityAlive() || cachedPrevNodeEntity.getEntityId() != getDataManager().get(DW_PREV_NODE)) {
            Entity entity = world.getEntityByID(getDataManager().get(DW_PREV_NODE));
            cachedPrevNodeEntity = entity;
            return entity;
        }
        return cachedPrevNodeEntity;
    }

    private Entity getNextNodeServer() {
        Entity nextNode = getNextNodeByUUID();

        if (nextNode != null && nextNode.getEntityId() != cachedNextNodeDW) {
            getDataManager().set(DW_NEXT_NODE, nextNode.getEntityId());
            cachedNextNodeDW = nextNode.getEntityId();
        }
        else if (nextNode == null && cachedNextNodeDW != -1) {
            getDataManager().set(DW_NEXT_NODE, -1);
            cachedNextNodeDW = -1;
        }
        return nextNode;
    }

    private Entity getPreviousNodeServer() {
        Entity prevNode = getPreviousNodeByUUID();
        if (prevNode != null && prevNode.getEntityId() != cachedPrevNodeDW) {
            getDataManager().set(DW_PREV_NODE, prevNode.getEntityId());
            cachedPrevNodeDW = prevNode.getEntityId();
        }
        else if (prevNode == null && cachedPrevNodeDW != -1) {
            getDataManager().set(DW_PREV_NODE, -1);
            cachedPrevNodeDW = -1;
        }
        return prevNode;
    }

    private Entity getEntityByUUID(UUID uuid) {
        for (Entity entity : world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(24, 24, 24)))
            if (uuid.equals(entity.getUniqueID()))
                return entity;
        return null;
    }
}