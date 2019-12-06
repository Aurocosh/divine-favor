package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.text.TextComponentString
import java.util.*

class SpellTalismanClock(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {}

    override fun performActionClient(context: CastContext) {
        val time = context.world.worldTime
        context.player.sendMessage(TextComponentString("Time: $time"))
        context.player.sendMessage(TextComponentString("Day time: " + time % 24000))
    }
}
