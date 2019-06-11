package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack

fun InventoryPlayer.allInvSequence(): Sequence<ItemStack> {
    return this.mainInventory.S + this.armorInventory.S + this.offHandInventory.S
}
