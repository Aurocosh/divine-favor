package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler
import net.minecraft.entity.player.EntityPlayer

val EntityPlayer.divinePlayerData: IPlayerDataHandler
    get() = this.getCapability(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!, null)!!

