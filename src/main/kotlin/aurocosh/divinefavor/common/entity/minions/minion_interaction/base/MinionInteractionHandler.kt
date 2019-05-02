package aurocosh.divinefavor.common.entity.minions.minion_interaction.base

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionInteraction
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import java.util.*

class MinionInteractionHandler<T> where T : IMinion, T : EntityLiving {
    private val interactions: MutableList<MinionInteraction<T>>
    private val ownerInteractions: MutableList<MinionInteraction<T>>

    init {
        interactions = ArrayList()
        ownerInteractions = ArrayList()
    }

    fun addInteraction(interaction: MinionInteraction<T>) {
        interactions.add(interaction)
    }

    fun addOwnerInteraction(interaction: MinionInteraction<T>) {
        ownerInteractions.add(interaction)
    }

    fun processInteract(minion: T, player: EntityPlayer, hand: EnumHand): Boolean {
        if (player.world.isRemote)
            return false

        val uuid = minion.minionData.ownerUUID
        if (player.gameProfile.id == uuid)
            for (interaction in ownerInteractions)
                if (interaction.process(minion, player, hand))
                    return true

        for (interaction in interactions)
            if (interaction.process(minion, player, hand))
                return true
        return false
    }
}
