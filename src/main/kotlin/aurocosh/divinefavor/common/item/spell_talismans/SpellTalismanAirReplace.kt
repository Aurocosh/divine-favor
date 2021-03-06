package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.Block
import java.util.*

class SpellTalismanAirReplace// cant pass blockDirectly they are not initialized yet
(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val blockProvider: () -> Block) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun validate(context: CastContext): Boolean {
        val pos = context.pos.offset(context.facing)
        return UtilBlock.isAirOrReplaceable(context.world, pos)
    }

    override fun performActionServer(context: CastContext) {
        val pos = context.pos.offset(context.facing)
        UtilBlock.replaceBlock(context.player, context.world, pos, blockProvider.invoke().defaultState)
    }
}
