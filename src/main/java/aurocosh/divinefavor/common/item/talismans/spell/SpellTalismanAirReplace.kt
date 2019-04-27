package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.Block
import java.util.*

class SpellTalismanAirReplace// cant pass blockDirectly they are not initialized yet
(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>, private val blockProvider: () -> Block) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun validate(context: TalismanContext): Boolean {
        val pos = context.pos.offset(context.facing)
        return UtilBlock.isAirOrReplaceable(context.world, pos)
    }

    override fun performActionServer(context: TalismanContext) {
        val pos = context.pos.offset(context.facing)
        UtilBlock.replaceBlock(context.player, context.world, pos, blockProvider.invoke().defaultState)
    }
}
