package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.material.Material
import java.util.*

class SpellTalismanEarthenDive(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val world = context.world
        val targetPos = UtilCoordinates.findPlaceToStandBelow(context.pos, world, ConfigSpell.earthenDive.maxDistance, true) ?: return

        val lavaIsClose = UtilCoordinates.getBlocksInSphere(targetPos, 2)
                .map(world::getBlockState)
                .any { it.material == Material.LAVA }
        if (!lavaIsClose)
            UtilEntity.teleport(context.player, targetPos)
    }
}
