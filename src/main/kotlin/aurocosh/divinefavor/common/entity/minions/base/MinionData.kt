package aurocosh.divinefavor.common.entity.minions.base

import com.google.common.base.Optional
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.EntityDataManager
import java.util.*

class MinionData<T>(private val minion: T, private val dataManager: EntityDataManager, private val beggingParameter: DataParameter<Boolean>, private val modeParameter: DataParameter<Int>, private val ownerUniqueIdParameter: DataParameter<Optional<UUID>>) where T : IMinion, T : EntityCreature {

    var attackTarget: EntityLivingBase? = null

    var isBegging: Boolean
        get() = dataManager.get(beggingParameter)
        set(beg) = dataManager.set(beggingParameter, beg)

    var ownerUUID: UUID?
        get() = (dataManager.get(ownerUniqueIdParameter) as Optional<*>).orNull() as UUID?
        set(ownerUUID) = dataManager.set(ownerUniqueIdParameter, Optional.fromNullable(ownerUUID))

    var owner: EntityPlayer?
        get() {
            try {
                val uuid = ownerUUID
                return if (uuid == null) null else minion.world.getPlayerEntityByUUID(uuid)
            } catch (var2: IllegalArgumentException) {
                return null
            }
        }
        set(owner) {
            ownerUUID = owner?.gameProfile?.id
        }

    val isFollowing: Boolean
        get() = mode === MinionMode.Follow

    val isWaiting: Boolean
        get() = mode === MinionMode.Wait

    var mode: MinionMode = MinionMode.Normal
        get() {
            return MinionMode.INDEXER[dataManager.get(modeParameter)]
        }
        set(value) {
            field = value
            dataManager.set(modeParameter, value.value)
        }

    fun hasOwner(): Boolean {
        return dataManager.get(ownerUniqueIdParameter).isPresent
    }

    fun isOwner(entityIn: EntityLivingBase): Boolean {
        return entityIn === owner
    }

    fun setOwnerByUUID(ownerUUID: String) {
        this.ownerUUID = if (ownerUUID.isEmpty()) null else UUID.fromString(ownerUUID)
    }

    fun writeEntityToNBT(compound: NBTTagCompound) {
        val ownerId = ownerUUID
        compound.setString(TAG_OWNER_UUID, ownerId?.toString() ?: "")
        compound.setInteger(TAG_MODE, mode.value)
    }

    fun readEntityFromNBT(compound: NBTTagCompound) {
        setOwnerByUUID(compound.getString(TAG_OWNER_UUID))
        mode = MinionMode.INDEXER[compound.getInteger(TAG_MODE)]
    }

    fun shouldAttack(entity: EntityLiving?): Boolean {
        val owner = owner
        if (entity === owner)
            return false
        if (entity is IMinion) {
            val minion = entity as IMinion
            if (minion.minionData.owner === owner)
                return false
        }
        return (entity as? EntityLivingBase)?.attackable() ?: false
    }

    companion object {
        private val TAG_OWNER_UUID = "OwnerUUID"
        private val TAG_MODE = "Mode"
    }
}
