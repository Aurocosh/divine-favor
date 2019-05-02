package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.potion.PotionEffect
import java.util.*

class SpellTalismanEscapePlan(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        player.divineCustomData.escapePlanData.globalPosition = GlobalBlockPos(context.player)
        player.addPotionEffect(PotionEffect(ModPotions.escape_plan, ConfigSpells.escapePlan.duration))
    }
}
