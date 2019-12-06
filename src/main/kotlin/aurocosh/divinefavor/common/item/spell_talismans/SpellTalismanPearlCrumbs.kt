package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import java.util.*

class SpellTalismanPearlCrumbs(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val player = context.player
        val crumbsData = player.divinePlayerData.pearlCrumbsData
        if (context.castType == CastType.UseCast)
            crumbsData.pushGlobalPosition(GlobalBlockPos(context.pos, context.player.dimension))
        else if (crumbsData.hasPositions())
            UtilEntity.teleport(context.player, crumbsData.popGlobalPosition())
    }

    public override fun isConsumeCharge(context: CastContext): Boolean {
        if (context.castType == CastType.UseCast)
            return false
        val crumbsData = context.player.divinePlayerData.pearlCrumbsData
        return crumbsData.hasPositions()
    }
}
