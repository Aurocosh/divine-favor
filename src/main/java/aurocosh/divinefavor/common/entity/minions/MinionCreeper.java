package aurocosh.divinefavor.common.entity.minions;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import aurocosh.divinefavor.common.entity.minions.behaviour.MinionBehaviourCreeper;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionFeeding;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionWaitSwitch;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.base.MinionInteractionHandler;
import com.google.common.base.Optional;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.UUID;

public class MinionCreeper extends EntityCreeper implements IMinion {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(MinionCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MODE = EntityDataManager.createKey(MinionCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(MinionCreeper.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private final MinionData<MinionCreeper> minionData;
    private final MinionInteractionHandler<MinionCreeper> interactionHandler;

    public MinionCreeper(World worldIn) {
        super(worldIn);
        minionData = new MinionData<>(this, dataManager, BEGGING, MODE, OWNER_UNIQUE_ID);
        minionData.setMode(MinionMode.Normal);

        MinionBehaviourCreeper<MinionCreeper> behaviour = new MinionBehaviourCreeper<>();
        behaviour.apply(this, tasks, targetTasks);

        interactionHandler = new MinionInteractionHandler<>();
        interactionHandler.addInteraction(new MinionWaitSwitch<>());
        interactionHandler.addInteraction(new MinionFeeding<>(1, Items.CHICKEN, Items.PORKCHOP, Items.BEEF));
    }

    @Override
    protected void initEntityAI() {
        // unused because it is called before all data initialized by child classes
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(MODE, MinionMode.Normal.getValue());
        dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
        dataManager.register(BEGGING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        minionData.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        minionData.readEntityFromNBT(compound);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return minionData.isOwner(player);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        return interactionHandler.processInteract(this, player, hand);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    public MinionData getMinionData() {
        return minionData;
    }
}
