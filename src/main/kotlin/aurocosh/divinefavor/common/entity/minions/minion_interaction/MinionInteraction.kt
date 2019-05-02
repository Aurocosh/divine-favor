package aurocosh.divinefavor.common.entity.minions.minion_interaction

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand

abstract class MinionInteraction<T> where T : IMinion, T : EntityLiving {
    abstract fun process(minion: T, player: EntityPlayer, hand: EnumHand): Boolean
}
