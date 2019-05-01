package aurocosh.divinefavor.common.custom_data.player

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler
import net.minecraft.entity.player.EntityPlayer

// The default implementation of the capability. Holds all the logic.
object PlayerData {
    operator fun get(player: EntityPlayer): IPlayerDataHandler? {
        return player.getCapability(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!, null)
    }
}
