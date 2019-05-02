package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.text.TextComponentString
import java.util.*

class SpellTalismanClock(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {}

    override fun performActionClient(context: TalismanContext) {
        val time = context.world.worldTime
        context.player.sendMessage(TextComponentString("Time: $time"))
        context.player.sendMessage(TextComponentString("Day time: " + time % 24000))
    }
}
