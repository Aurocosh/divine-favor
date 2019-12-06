package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanHellisphere(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    val materials = hashSetOf(Material.ROCK, Material.GROUND, Material.CLAY, Material.SAND)

    override fun performActionServer(context: CastContext) {
        val world = context.world
        val state = Blocks.LAVA.defaultState
        val spherePoints = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpell.hellisphere.radius)
        val smeltPositions = spherePoints.filter { pos -> !world.isAirBlock(pos) && materials.contains( world.getBlockState(pos).material )}

        for (pos in smeltPositions)
            UtilBlock.replaceBlock(context.player, world, pos, state)
    }
}
