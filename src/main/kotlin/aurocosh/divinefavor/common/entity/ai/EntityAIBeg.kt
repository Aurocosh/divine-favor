package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import java.util.*

open class EntityAIBeg<T>(protected val minion: T, private val minPlayerDistance: Float, vararg wantedItems: Item) : EntityAIBase() where T : IMinion, T : EntityLiving {
    private var player: EntityPlayer? = null
    private val world: World = minion.world
    private val minPlayerDistanceSq: Float = minPlayerDistance * minPlayerDistance
    private var timeoutCounter: Int = 0
    private val wantedItems: List<Item> = Arrays.asList(*wantedItems)

    init {
        mutexBits = 2
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    override fun shouldExecute(): Boolean {
        player = world.getClosestPlayerToEntity(minion, minPlayerDistance.toDouble())
        return player != null && wantSomethingFromPlayer(player as EntityPlayer)
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    override fun shouldContinueExecuting(): Boolean {
        if (!player!!.isEntityAlive)
            return false
        return if (minPlayerDistanceSq < minion.getDistanceSq(player!!)) false else timeoutCounter > 0 && wantSomethingFromPlayer(player as EntityPlayer)
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        minion.minionData.isBegging = true
        timeoutCounter = 40 + minion.rng.nextInt(40)
    }

    /**
     * Resets the task
     */
    override fun resetTask() {

        minion.minionData.isBegging = false
        player = null
    }

    /**
     * Updates the task
     */
    override fun updateTask() {
        minion.lookHelper.setLookPosition(player!!.posX, player!!.posY + player!!.getEyeHeight(), player!!.posZ, 10.0f, minion.verticalFaceSpeed.toFloat())
        --timeoutCounter
    }

    protected open fun wantSomethingFromPlayer(player: EntityPlayer): Boolean {
        for (hand in EnumHand.values()) {
            val item = player.getHeldItem(hand).item
            for (wantedItem in wantedItems)
                if (wantedItem === item)
                    return true
        }
        return false
    }
}
