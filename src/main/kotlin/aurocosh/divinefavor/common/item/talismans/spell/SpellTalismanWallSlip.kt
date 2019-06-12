package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.util.EnumFacing
import java.util.*

class SpellTalismanWallSlip(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val facing = context.facing
        if (facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            return
        val pos = UtilCoordinates.findPlaceToTeleport(context.pos.down(), context.world, facing.opposite, ConfigSpell.wallSlip.maxDistance, false)
        if (pos != null)
            UtilEntity.teleport(context.player, pos.down())
    }
}
