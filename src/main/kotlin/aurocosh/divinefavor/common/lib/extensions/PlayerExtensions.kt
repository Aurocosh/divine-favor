package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler
import aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.IItemHandler

val EntityPlayer.divinePlayerData: IPlayerDataHandler
    get() = this.getCapability(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA, null) as IPlayerDataHandler

fun EntityPlayer.getInventoryCapability(): IItemHandler {
    return this.getCapability(ITEM_HANDLER_CAPABILITY, null) as IItemHandler
}

fun EntityPlayer.getAllInventoryCapabilities(): Sequence<IItemHandler> {
    return listOf(getInventoryCapability()).S + getInventoryCapability().asSequence().mapNotNull { it.capNull(ITEM_HANDLER_CAPABILITY) }
}
