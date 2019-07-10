package aurocosh.divinefavor.common.item.spell_talismans.template.converters

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.FaceDiagonalOrientation
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilTemplate
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.collections.HashMap

abstract class BlockTemplateConverter {
    abstract fun convertOrientation(blockTemplate: BlockTemplate): FaceDiagonalOrientation

    fun convert(player: EntityPlayer) {
        val currentTemplate = player.divinePlayerData.templateData.currentTemplate
        val templateSavedData = player.world[TemplateData]
        val blockTemplate = templateSavedData[currentTemplate] ?: return

        val newOrientation = convertOrientation(blockTemplate)
        val variation = templateSavedData.getVariation(blockTemplate.uuid, newOrientation)
        if (variation != null) {
            UtilTemplate.setCurrent(player, variation)
            return
        }

        val rotatedOffsets = rotatedOffsets(blockTemplate)
        val shiftedOrigin = CuboidBoundingBox.getBoundingBox(rotatedOffsets).lowerCorner

        val fixedOffsets = rotatedOffsets.map { it.subtract(shiftedOrigin) }
        val newPosIntArray = fixedOffsets.map(UtilBlockPos::blockPosToInt).toIntArray()

        val newBoundingBox = CuboidBoundingBox.getBoundingBox(fixedOffsets)
        val newBlockMapIntState = rotateBlockMapIntState(blockTemplate.blockMapIntState)

        val uuid = UUID.randomUUID()
        val template = BlockTemplate(uuid, newBlockMapIntState, newPosIntArray, blockTemplate.stateIntArray, newBoundingBox, blockTemplate.dimension, player.name, newOrientation)
        templateSavedData[uuid] = template
        UtilTemplate.setCurrent(player, template)
        templateSavedData.addVariation(blockTemplate.uuid, uuid, newOrientation)
    }

    private fun rotateBlockMapIntState(blockMapIntState: BlockMapIntState): BlockMapIntState {
        val intStateMap = blockMapIntState.intStateMap
        val intStackMap = blockMapIntState.intStackMap

        val newIntStateMap = HashMap<Short, IBlockState>()
        val newIntStackMap = HashMap<IBlockState, MetaItem>()

        for ((stateId, state) in intStateMap) {
            val metaItem = intStackMap[state] ?: MetaItem()
            val rotatedState = rotateState(state)

            newIntStateMap[stateId] = rotatedState
            newIntStackMap[rotatedState] = metaItem
        }

        return BlockMapIntState(newIntStateMap, newIntStackMap)
    }

    abstract fun rotatedOffsets(blockTemplate: BlockTemplate): List<BlockPos>
    abstract fun rotateState(state: IBlockState): IBlockState
}
