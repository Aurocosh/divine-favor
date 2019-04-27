package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.item.talismans.spell.base.CastType
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.lib.GlobalBlockPos
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import java.util.*

class SpellTalismanPearlCrumbs(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val crumbsData = PlayerData.get(player).pearlCrumbsData
        if (context.castType == CastType.UseCast)
            crumbsData.pushGlobalPosition(GlobalBlockPos(context.pos, context.player.dimension))
        else if (crumbsData.hasPositions())
            UtilEntity.teleport(context.player, crumbsData.popGlobalPosition())
    }

    public override fun isConsumeCharge(context: TalismanContext): Boolean {
        if (context.castType == CastType.UseCast)
            return false
        val crumbsData = PlayerData.get(context.player).pearlCrumbsData
        return crumbsData.hasPositions()
    }
}
