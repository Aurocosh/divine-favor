package aurocosh.divinefavor.common.entity.minions.base;

import com.google.common.base.Optional;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

import javax.annotation.Nullable;
import java.util.UUID;

public class MinionData<T extends EntityCreature & IMinion> {
    private static final String TAG_OWNER_UUID = "OwnerUUID";
    private static final String TAG_MODE = "Mode";

    private final T minion;
    private final EntityDataManager dataManager;

    private final DataParameter<Boolean> begging;
    private final DataParameter<Integer> mode;
    private final DataParameter<Optional<UUID>> ownerUniqueId;

    private EntityLivingBase attackTarget;

    public MinionData(T minion, EntityDataManager dataManager, DataParameter<Boolean> begging, DataParameter<Integer> mode, DataParameter<Optional<UUID>> ownerUniqueId) {
        this.minion = minion;
        this.dataManager = dataManager;
        this.begging = begging;
        this.mode = mode;
        this.ownerUniqueId = ownerUniqueId;
    }

    public MinionMode getMode() {
        return MinionMode.VALUES[dataManager.get(mode)];
    }

    public void setMode(MinionMode minionMode) {
        dataManager.set(mode, minionMode.getValue());
    }

    public boolean isBegging() {
        return dataManager.get(begging);
    }

    public void setBegging(boolean beg) {
        dataManager.set(begging, beg);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return (UUID) ((Optional) dataManager.get(ownerUniqueId)).orNull();
    }

    public void setOwnerUUID(UUID ownerUUID) {
        dataManager.set(ownerUniqueId, Optional.fromNullable(ownerUUID));
    }

    @Nullable
    public EntityPlayer getOwner() {
        try {
            UUID uuid = getOwnerUUID();
            return uuid == null ? null : minion.world.getPlayerEntityByUUID(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Nullable
    public void setOwner(EntityPlayer owner) {
        setOwnerUUID(owner.getGameProfile().getId());
    }

    @Nullable
    public boolean hasOwner() {
        return (dataManager.get(ownerUniqueId)).isPresent();
    }

    public boolean isOwner(EntityLivingBase entityIn) {
        return entityIn == getOwner();
    }

    public void setOwnerByUUID(String ownerUUID) {
        setOwnerUUID(ownerUUID.isEmpty() ? null : UUID.fromString(ownerUUID));
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        UUID ownerId = getOwnerUUID();
        compound.setString(TAG_OWNER_UUID, ownerId == null ? "" : ownerId.toString());
        compound.setInteger(TAG_MODE, getMode().getValue());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        setOwnerByUUID(compound.getString(TAG_OWNER_UUID));
        setMode(MinionMode.fromInt(compound.getInteger(TAG_MODE)));
    }

    public EntityLivingBase getAttackTarget() {
        return attackTarget;
    }

    public void setAttackTarget(EntityLivingBase livingBase){
        attackTarget = livingBase;
    }
}
