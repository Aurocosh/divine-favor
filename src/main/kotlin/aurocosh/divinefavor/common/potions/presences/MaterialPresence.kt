package aurocosh.divinefavor.common.potions.presences

import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilSpirit
import net.minecraft.entity.player.EntityPlayer

object MaterialPresence {
    fun onInviteGiven(player: EntityPlayer) {
        val presenceData = player.divinePlayerData.materialPresenceData
        if (!presenceData.tryLuck())
            return
        presenceData.reset()
        UtilSpirit.convertMarksToInvites(player, ModSpirits.materia, ModCallingStones.calling_stone_materia)
    }
}