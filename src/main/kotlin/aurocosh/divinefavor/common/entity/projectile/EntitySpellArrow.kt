package aurocosh.divinefavor.common.entity.projectile

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.util.UtilSerialize
import com.google.common.base.Optional
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*
import javax.vecmath.Color3f

open class EntitySpellArrow : EntityArrow {
    private val gravityValue = 0.05

    var color: Color3f = Color3f(1f, 1f, 1f)
        private set
    private var shooter: EntityPlayer? = null
    private var entityIgnoreTicks = 0
    private var talisman: ItemArrowTalisman? = null
    private var hasAntiGrav = false

    var talismanDataServer: NBTTagCompound = NBTTagCompound()
    var talismanDataCommon: NBTTagCompound = NBTTagCompound()
        set (compound) {
            field = compound
            dataManager.set(TALISMAN_DATA_COMMON, field)
        }

    val isInGround: Boolean
        get() = inGround

    var talismanId: String
        get() = dataManager.get(TALISMAN_ID)
        private set(string) = dataManager.set(TALISMAN_ID, string)

    val arrowType: ArrowType
        get() = ArrowType[dataManager.get(TYPE)]

    val colorInt: Int
        get() = dataManager.get(COLOR)

    constructor(worldIn: World) : super(worldIn)

    constructor(worldIn: World, x: Double, y: Double, z: Double) : super(worldIn, x, y, z)

    constructor(worldIn: World, shooter: EntityLivingBase) : super(worldIn, shooter)

    fun setSpell(talisman: ItemArrowTalisman, shooter: EntityPlayer) {
        color = Color3f(1f, 1f, 1f)
        this.talisman = talisman
        this.shooter = shooter
        setShooterId(shooter.gameProfile.id)
        setColor(talisman.color.rgb)
        setArrowType(talisman.arrowType.value)
        talismanId = talisman.registryName.toString()
    }

    override fun entityInit() {
        super.entityInit()
        dataManager.register(COLOR, -1)
        dataManager.register(TYPE, ArrowType.WOODEN_ARROW.value)
        dataManager.register(TALISMAN_ID, "")
        dataManager.register(HAS_ANTI_GRAVITY, true)
        dataManager.register(DESPAWN_DELAY, 1200)
        dataManager.register(ENTITY_IGNORE_DELAY, 0)
        dataManager.register(TALISMAN_DATA_COMMON, NBTTagCompound())
        dataManager.register(SHOOTER_UUID, Optional.absent())
    }

    /**
     * Called to update the entity's position/logic.
     */
    override fun onUpdate() {
        super.onUpdate()
        if (hasAntiGrav)
            this.motionY += gravityValue
        if (world.isRemote) {
            if (!inGround)
                spawnPotionParticles(2)
            else if (timeInGround % 5 == 0)
                spawnPotionParticles(1)
            talisman?.spawnParticles(this)
        }
        talisman?.onUpdate(this)
    }

    private fun spawnPotionParticles(particleCount: Int) {
        if (colorInt != -1 && particleCount > 0) {
            for (j in 0 until particleCount)
                world.spawnParticle(EnumParticleTypes.SPELL_MOB, posX + (rand.nextDouble() - 0.5) * width.toDouble(), posY + rand.nextDouble() * height.toDouble(), posZ + (rand.nextDouble() - 0.5) * width.toDouble(), color.x.toDouble(), color.y.toDouble(), color.z.toDouble())
        }
    }


    //    protected void arrowHit(EntityLivingBase living) {
    //        if (talisman != null && shooter != null)
    //            talisman.getSpell().cast(living, shooter, this);
    //    }

    override fun onHit(raytraceResultIn: RayTraceResult) {
        var hit = true
        val talisman = talisman
        val shooter = shooter
        if (talisman != null && shooter != null) {
            val entity = raytraceResultIn.entityHit
            val living = if (entity is EntityLivingBase) entity else null
            hit = talisman.cast(living, shooter, this, raytraceResultIn.blockPos, raytraceResultIn.sideHit)
            if (hit && talisman.isBreakOnHit)
                setDead()
        }
        if (hit)
            super.onHit(raytraceResultIn)
    }

    override fun findEntityOnPath(start: Vec3d, end: Vec3d): Entity? {
        return if (entityIgnoreTicks > 0) {
            entityIgnoreTicks--
            null
        } else
            super.findEntityOnPath(start, end)
    }

    override fun getArrowStack(): ItemStack {
        return ItemStack(Items.ARROW)
    }

    @SideOnly(Side.CLIENT)
    override fun handleStatusUpdate(id: Byte) {
        if (id.toInt() == 0) {
            if (colorInt != -1) {
                for (j in 0..19)
                    world.spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, posX + (rand.nextDouble() - 0.5) * width.toDouble(), posY + rand.nextDouble() * height.toDouble(), posZ + (rand.nextDouble() - 0.5) * width.toDouble(), color.x.toDouble(), color.y.toDouble(), color.z.toDouble())
            }
        } else
            super.handleStatusUpdate(id)
    }

    private fun getTalisman(): ItemArrowTalisman? {
        val item = Item.getByNameOrId(dataManager.get(TALISMAN_ID))
        return if (item is ItemArrowTalisman) item else null
    }

    fun setHasAntiGravity(hasAntiGravity: Boolean) {
        hasAntiGrav = hasAntiGravity
        this.dataManager.set(HAS_ANTI_GRAVITY, hasAntiGravity)
    }

    fun setDespawnDelay(delay: Int) {
        timeInGround = 1200 - delay
        dataManager.set(DESPAWN_DELAY, delay)
    }

    fun setEntityIgnoreDelay(delay: Int) {
        entityIgnoreTicks = delay
        dataManager.set(ENTITY_IGNORE_DELAY, delay)
    }


    override fun onCollideWithPlayer(player: EntityPlayer) {
        talisman?.onCollideWithPlayer(this, player) ?: super.onCollideWithPlayer(player)
    }

    override fun notifyDataManagerChange(key: DataParameter<*>) {
        super.notifyDataManagerChange(key)
        when {
            TALISMAN_ID == key -> talisman = getTalisman()
            DESPAWN_DELAY == key -> setDespawnDelay(dataManager.get(DESPAWN_DELAY))
            ENTITY_IGNORE_DELAY == key -> entityIgnoreTicks = dataManager.get(ENTITY_IGNORE_DELAY)
            HAS_ANTI_GRAVITY == key -> hasAntiGrav = dataManager.get(HAS_ANTI_GRAVITY)
            TALISMAN_DATA_COMMON == key -> talismanDataCommon = dataManager.get(TALISMAN_DATA_COMMON)
            SHOOTER_UUID == key -> {
                val uuid = dataManager.get(SHOOTER_UUID).orNull()
                shooter = if (uuid == null) null else world.getPlayerEntityByUUID(uuid)
                setShooterId(uuid)
            }
            COLOR == key -> setColor(colorInt)
        }
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        super.writeEntityToNBT(compound)
        val shooter = shooter

        compound.setInteger(TAG_COLOR, colorInt)
        compound.setInteger(TAG_ARROW_TYPE, arrowType.value)
        compound.setString(TAG_TALISMAN, talismanId)
        compound.setBoolean(TAG_ANTI_GRAV, hasAntiGrav)
        compound.setInteger(TAG_IGNORE_DELAY, entityIgnoreTicks)
        compound.setTag(TAG_TALISMAN_DATA_COMMON, talismanDataCommon)
        compound.setTag(TAG_TALISMAN_DATA_SERVER, this.talismanDataServer)
        compound.setString(TAG_SHOOTER, if (shooter == null) "" else shooter.gameProfile.id.toString())
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        super.readEntityFromNBT(compound)

        setColor(compound.getInteger(TAG_COLOR))
        setArrowType(compound.getInteger(TAG_ARROW_TYPE))
        val talismanId = compound.getString(TAG_TALISMAN)
        setHasAntiGravity(compound.getBoolean(TAG_ANTI_GRAV))
        setDespawnDelay(timeInGround + 1200)
        setEntityIgnoreDelay(compound.getInteger(TAG_IGNORE_DELAY))
        talismanDataCommon = compound.getCompoundTag(TAG_TALISMAN_DATA_COMMON)
        talismanDataServer = compound.getCompoundTag(TAG_TALISMAN_DATA_SERVER)

        val uuid = UtilSerialize.stringToUUID(compound.getString(TAG_SHOOTER))
        shooter = if (uuid == null) null else world.getPlayerEntityByUUID(uuid)
        setShooterId(uuid)

        val item = Item.getByNameOrId(talismanId)
        if (item is ItemArrowTalisman)
            talisman = item
    }

    private fun setShooterId(shooterId: UUID?) {
        dataManager.set(SHOOTER_UUID, Optional.fromNullable(shooterId))
    }

    private fun setArrowType(value: Int) {
        dataManager.set(TYPE, ArrowType[value].value)
    }

    private fun setColor(colorInt: Int) {
        if (colorInt != -1)
            color = Color3f(Color(colorInt))
        dataManager.set(COLOR, colorInt)
    }

    companion object {
        private val DESPAWN_DELAY = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.VARINT)
        private val ENTITY_IGNORE_DELAY = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.VARINT)
        private val SHOOTER_UUID = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.OPTIONAL_UNIQUE_ID)
        private val TALISMAN_DATA_COMMON = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.COMPOUND_TAG)

        private const val TAG_COLOR = "Color"
        private const val TAG_ARROW_TYPE = "ArrowType"
        private const val TAG_TALISMAN = "Talisman"
        private const val TAG_IGNORE_DELAY = "IgnoreDelay"
        private const val TAG_ANTI_GRAV = "AntiGrav"
        private const val TAG_SHOOTER = "Shooter"
        private const val TAG_TALISMAN_DATA_COMMON = "TalismanDataCommon"
        private const val TAG_TALISMAN_DATA_SERVER = "TalismanDataServer"

        private val COLOR = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.VARINT)
        private val TYPE = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.VARINT)
        private val TALISMAN_ID = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.STRING)
        private val HAS_ANTI_GRAVITY = EntityDataManager.createKey(EntitySpellArrow::class.java, DataSerializers.BOOLEAN)
    }
}