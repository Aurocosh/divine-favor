package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanHellisphere(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val state = Blocks.LAVA.defaultState
        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.hellisphere.radius)
        val smeltPositions = spherePoints.filter { pos -> !world.isAirBlock(pos) }
        for (pos in smeltPositions)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
