package aurocosh.divinefavor.common.entity.rope

import aurocosh.divinefavor.common.config.common.ConfigRope
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class EntityRopeExplosiveNode(world: World) : EntityRopeNodeBase(world) {

    private var triggered: Boolean = false
    var fuse: Int = 0
        set(value) {
            dataManager.set(FUSE, value)
            field = value
        }

    override val entityClass: Class<out EntityRopeNodeBase>
        get() = EntityRopeExplosiveNode::class.java

    override val isEmittingLight: Boolean
        get() = true

    init {
        setSize(0.2f, 0.2f)
        triggered = false
        fuse = ConfigRope.explosiveRope.fuseDelay
        preventEntitySpawning = true
        isImmuneToFire = true
    }

    override fun entityInit() {
        super.entityInit()
        dataManager.register(TRIGGERED, false)
        dataManager.register(FUSE, ConfigRope.explosiveRope.fuseDelay)
    }

    override fun onEntityUpdate() {
        super.onEntityUpdate()

        if (!triggered)
            if (world.isFlammableWithin(this.entityBoundingBox.shrink(0.001)))
                setTriggered(true)

        if (triggered && --fuse <= 0) {
            setDead()

            if (!world.isRemote) {
                triggerNeighbour(nextNode)
                triggerNeighbour(prevNode)

                world.createExplosion(this, posX, posY, posZ, ConfigRope.explosiveRope.explosionPower, true)
            }
        }
    }

    private fun triggerNeighbour(entity: Entity?) {
        if (entity is EntityRopeExplosiveNode) {
            val explosiveNode = entity as EntityRopeExplosiveNode?
            triggerNode(explosiveNode!!)
        }
    }

    private fun triggerNode(explosiveNode: EntityRopeExplosiveNode) {
        if (!explosiveNode.getTriggered()) {
            explosiveNode.setTriggered(true)
            fuse = UtilRandom.nextInt(1, 5)
        }
    }

    override fun readEntityFromNBT(nbt: NBTTagCompound) {
        super.readEntityFromNBT(nbt)
        setTriggered(nbt.getBoolean(TAG_TRIGGERED))
        fuse = nbt.getShort(TAG_FUSE).toInt()
    }

    override fun writeEntityToNBT(nbt: NBTTagCompound) {
        super.writeEntityToNBT(nbt)
        nbt.setBoolean(TAG_TRIGGERED, getTriggered())
        nbt.setShort(TAG_FUSE, fuse.toShort())
    }

    fun getTriggered(): Boolean {
        return dataManager.get(TRIGGERED)
    }

    fun setTriggered(value: Boolean) {
        dataManager.set(TRIGGERED, value)
        triggered = value
    }


    override fun notifyDataManagerChange(key: DataParameter<*>?) {
        if (FUSE == key)
            this.fuse = dataManager.get(FUSE)
        if (TRIGGERED == key)
            this.triggered = this.getTriggered()
    }

    override fun registerPickUp(player: EntityPlayer) {
        UtilPlayer.addStackToInventoryOrDrop(player, ItemStack(ModItems.rope_explosive, 1))
    }

    override fun canDropNewNode(player: EntityPlayer): Boolean {
        val (slotIndex, stack) = UtilPlayer.findStackInInventory(player) { element -> !element.isEmpty && element.item === ModItems.rope_explosive }
        if (slotIndex == -1)
            return false
        stack.shrink(1)
        player.inventory.setInventorySlotContents(slotIndex, if (stack.count > 0) stack else ItemStack.EMPTY)
        return true
    }

    override fun makeNewNode(world: World): EntityRopeNodeBase {
        return EntityRopeExplosiveNode(world)
    }

    override fun processInitialInteract(player: EntityPlayer, hand: EnumHand?): Boolean {
        if (world.isRemote)
            return true

        val item = player.getHeldItem(hand).item
        if (item === Items.FLINT_AND_STEEL) {
            setTriggered(true)
            var nextNode = nextNode
            if (nextNode is EntityPlayer)
                nextNode = null
            return true
        } else
            return super.processInitialInteract(player, hand)
    }

    @SideOnly(Side.CLIENT)
    override fun canRenderOnFire(): Boolean {
        return false
    }

    override fun attackEntityFrom(source: DamageSource, amount: Float): Boolean {
        if (source.isExplosion)
            triggerNode(this)
        return super.attackEntityFrom(source, amount)
    }

    companion object {
        private val TRIGGERED = EntityDataManager.createKey(EntityRopeExplosiveNode::class.java, DataSerializers.BOOLEAN)
        private val FUSE = EntityDataManager.createKey(EntityRopeExplosiveNode::class.java, DataSerializers.VARINT)
        private val TAG_TRIGGERED = "Triggered"
        private val TAG_FUSE = "Fuse"
    }
}