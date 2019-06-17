package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.item.crafting.FurnaceRecipes
import java.util.*

class SpellTalismanBlazingPalm(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val hand = UtilPlayer.getOtherHand(context.hand)
        val player = context.player
        val stack = player.getHeldItem(hand)
        val resultTemplate = FurnaceRecipes.instance().getSmeltingResult(stack)
        if (resultTemplate.isEmpty)
            return
        val result = resultTemplate.copy()
        result.count = stack.count

        val slotIndex = UtilPlayer.getHandIndex(player, hand)
        player.inventory.setInventorySlotContents(slotIndex, result)
    }
}
