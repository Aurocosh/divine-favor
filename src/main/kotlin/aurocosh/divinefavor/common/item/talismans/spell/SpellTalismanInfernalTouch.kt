package aurocosh.divinefavor.common.item.talismans.spell

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilMaterial
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import java.util.*

class SpellTalismanInfernalTouch(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val state = context.world.getBlockState(context.pos)

        var result: Block? = blocks[state.block]
        if (result == null)
            result = materials[state.material]

        if (result != null)
            UtilBlock.replaceBlock(context.player, context.world, context.pos, result.defaultState)
    }

    companion object {
        private val blocks = HashMap<Block, Block>()
        private val materials = HashMap<Material, Block>()

        init {
            for ((blockName, resultName) in ConfigSpells.infernalTouch.blockConversions) {
                val block = Block.getBlockFromName(blockName)
                if (block == null) {
                    DivineFavor.logger.error("Block does not exist: $blockName")
                    continue
                }
                val result = Block.getBlockFromName(resultName)
                if (result == null) {
                    DivineFavor.logger.error("Block does not exist: $resultName")
                    continue
                }
                blocks[block] = result
            }

            for ((materialName, resultName) in ConfigSpells.infernalTouch.materialConversions) {
                val material = UtilMaterial.getMaterial(materialName)
                if (material == null) {
                    DivineFavor.logger.error("Material does not exist: $materialName")
                    continue
                }
                val result = Block.getBlockFromName(resultName)
                if (result == null) {
                    DivineFavor.logger.error("Block does not exist: $resultName")
                    continue
                }
                materials[material] = result
            }
        }
    }
}
