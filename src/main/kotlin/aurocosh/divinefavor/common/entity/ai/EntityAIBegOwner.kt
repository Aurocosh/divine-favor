package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item

class EntityAIBegOwner<T>(minion: T, minDistance: Float, vararg wantedItems: Item) : EntityAIBeg<T>(minion, minDistance, *wantedItems) where T : IMinion, T : EntityLiving {

    override fun wantSomethingFromPlayer(player: EntityPlayer): Boolean {
        return minion.minionData.isOwner(player) && super.wantSomethingFromPlayer(player)
    }
}
