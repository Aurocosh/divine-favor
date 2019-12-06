package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.gems.ItemInviteMarker
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanInviteMarker(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val stack = ItemStack(item)
        stack.set(ItemInviteMarker.playerId, context.player.gameProfile.id)
        stack.set(ItemInviteMarker.playerName, context.player.gameProfile.name)
        context.player.inventory.addItemStackToInventory(stack)
    }
}
