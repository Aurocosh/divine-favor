package aurocosh.divinefavor.common.entity.minions.minion_interaction

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.item.ItemBanishingWand
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand

class MinionBanishing<T> : MinionInteraction<T>() where T : IMinion, T : EntityLiving {
    override fun process(minion: T, player: EntityPlayer, hand: EnumHand): Boolean {
        val stack = player.getHeldItem(hand)
        if (stack.isEmpty)
            return false
        val item = stack.item
        if (item is ItemBanishingWand) {
            minion.setDead()
            return true
        }
        return false
    }
}
