package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.util.InventoryIndexes
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand

fun EnumHand.getOther(): EnumHand {
    return if (this == EnumHand.MAIN_HAND) EnumHand.OFF_HAND else EnumHand.MAIN_HAND
}

fun EnumHand.getHandIndex(player: EntityPlayer): Int {
    return if (this == EnumHand.MAIN_HAND) player.inventory.currentItem else InventoryIndexes.Offhand.value
}
