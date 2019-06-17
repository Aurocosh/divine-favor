package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.getOther
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.item.ItemBlock
import java.util.*

class SpellTalismanPlaceBlock(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun validate(context: TalismanContext): Boolean {
        val pos = context.pos.offset(context.facing)
        if (!UtilBlock.isAirOrReplaceable(context.world, pos))
            return false
        val heldItem = context.player.getHeldItem(context.hand.getOther())
        val item = heldItem.item
        return item is ItemBlock
    }

    override fun performActionServer(context: TalismanContext) {
        val pos = context.pos.offset(context.facing)

        val otherHand = context.hand.getOther()
        val heldItem = context.player.getHeldItem(otherHand)
        UtilBlock.replaceBlock(context.player, context.world, pos, heldItem, otherHand)
    }
}
