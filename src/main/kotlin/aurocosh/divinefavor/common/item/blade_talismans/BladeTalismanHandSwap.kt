package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer

class BladeTalismanHandSwap(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: CastContext) {
        if (context.target is EntityPlayer)
            UtilPlayer.swapStacks(context.target, context.target.inventory.currentItem, InventoryIndexes.Offhand.value)
    }
}
