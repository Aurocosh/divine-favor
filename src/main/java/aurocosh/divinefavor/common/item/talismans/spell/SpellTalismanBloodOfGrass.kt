package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks
import java.util.*

class SpellTalismanBloodOfGrass(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val world = context.world
        val player = context.player

        val posList = UtilCoordinates.getBlocksInSphere(player.position, ConfigSpells.bloodOfGrass.radius)
        val plantList = posList.filter { pos -> world.getBlockState(pos).material === Material.GRASS }
        for (pos in plantList) if (UtilBlock.replaceBlock(player, world, pos, Blocks.DIRT.defaultState))
            player.heal(ConfigSpells.bloodOfGrass.healthPerGrass)
    }
}
