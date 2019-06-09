package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class BladeTalismanHandSwap(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        if (target is EntityPlayer)
            UtilPlayer.swapStacks(target, target.inventory.currentItem, InventoryIndexes.Offhand.value)
    }
}
