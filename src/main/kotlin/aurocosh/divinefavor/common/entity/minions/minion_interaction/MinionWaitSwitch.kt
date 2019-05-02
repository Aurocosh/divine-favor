package aurocosh.divinefavor.common.entity.minions.minion_interaction

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand

class MinionWaitSwitch<T> : MinionInteraction<T>() where T : IMinion, T : EntityLiving {
    override fun process(minion: T, player: EntityPlayer, hand: EnumHand): Boolean {
        if (hand != EnumHand.MAIN_HAND)
            return false
        if (!player.heldItemMainhand.isEmpty)
            return false
        val minionData = minion.minionData
        if (!minionData.isOwner(player))
            return false
        minionData.mode = if (minionData.mode != MinionMode.Normal) MinionMode.Normal else MinionMode.Wait
        minion.attackTarget = null
        return true
    }
}
