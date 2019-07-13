package aurocosh.divinefavor.common.item.spell_talismans.move

import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.facingField
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import java.util.*

class SpellTalismanPushSide(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : SpellTalismanPullSide(name, spirit, favorCost, options) {
    override fun performActionServer(context: TalismanContext) {
        val (player, world, facing) = context.get(playerField, worldField, facingField)
        val coordinates = getCommonCoordinates(context)
        coordinates.forEach { UtilBlock.moveBlock(player, world, it, facing.opposite) }
    }
}
