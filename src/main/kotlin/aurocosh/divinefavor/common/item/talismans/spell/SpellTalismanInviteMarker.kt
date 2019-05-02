package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.ItemInviteMarker
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import java.util.*

class SpellTalismanInviteMarker(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val item: ModItem) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val stack = ItemStack(item)
        stack.compound.setString(ItemInviteMarker.TAG_PLAYER_UUID, context.player.gameProfile.id.toString())
        context.player.inventory.addItemStackToInventory(stack)
    }
}
