package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class EntityAIBeg<T extends EntityLiving & IMinion> extends EntityAIBase {
    protected final T minion;
    private EntityPlayer player;
    private final World world;
    private final float minPlayerDistance;
    private final float minPlayerDistanceSq;
    private int timeoutCounter;
    private final List<Item> wantedItems;

    public EntityAIBeg(T minion, float minDistance, Item... wantedItems) {
        this.minion = minion;
        world = minion.world;
        minPlayerDistance = minDistance;
        minPlayerDistanceSq = minDistance * minDistance;
        this.wantedItems = Arrays.asList(wantedItems);
        setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        player = world.getClosestPlayerToEntity(minion, minPlayerDistance);
        return player != null && wantSomethingFromPlayer(player);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if(!player.isEntityAlive())
            return false;
        if(minPlayerDistanceSq < minion.getDistanceSq(player))
            return false;
        return timeoutCounter > 0 && wantSomethingFromPlayer(player);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        minion.getMinionData().setBegging(true);
        timeoutCounter = 40 + minion.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {

        minion.getMinionData().setBegging(false);
        player = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        minion.getLookHelper().setLookPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ, 10.0F, minion.getVerticalFaceSpeed());
        --timeoutCounter;
    }

    protected boolean wantSomethingFromPlayer(EntityPlayer player) {
        for (EnumHand hand : EnumHand.values()) {
            Item item = player.getHeldItem(hand).getItem();
            for (Item wantedItem : wantedItems)
                if (wantedItem == item)
                    return true;
        }
        return false;
    }
}
