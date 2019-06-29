package aurocosh.divinefavor.common.block_templates

import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.extensions.getBlockState
import aurocosh.divinefavor.common.lib.extensions.getMap
import aurocosh.divinefavor.common.lib.extensions.setBlockState
import aurocosh.divinefavor.common.lib.extensions.setMap
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.math.BlockPos
import java.util.*

object BlockTemplateCompatibilitySerializer {
    private const val tagMapIntState = "mapIntState"
    private const val tagPosIntArray = "posIntArray"
    private const val tagStateIntArray = "stateIntArray"

    private const val tagStartPos = "startPos"
    private const val tagEndPos = "endPos"
    private const val tagDimension = "dim"

    private const val tagKey = "mapSlot"
    private const val tagValue = "mapState"

    fun serialize(template: BlockTemplate): NBTTagCompound {
        val compound = NBTTagCompound()

        compound.setMap(tagMapIntState, template.blockMapIntState.intStateMap, NBTTagCompound::setShort, NBTTagCompound::setBlockState, tagKey, tagValue)

        compound.setIntArray(tagPosIntArray, template.posIntArray)
        compound.setIntArray(tagStateIntArray, template.stateIntArray)

        compound.setTag(tagStartPos, NBTUtil.createPosTag(template.boundingBox.lowerCorner))
        compound.setTag(tagEndPos, NBTUtil.createPosTag(template.boundingBox.upperCorner))
        compound.setInteger(tagDimension, 0)

        return compound
    }

    fun deserialize(compound: NBTTagCompound, player: EntityPlayer): BlockTemplate {
        val intStateMap = compound.getMap(tagMapIntState, NBTTagCompound::getShort, NBTTagCompound::getBlockState, tagKey, tagValue)

        val startPos = posFromNbtTag(compound, tagStartPos)
        val endPos = posFromNbtTag(compound, tagEndPos)

        val boundingBox = CuboidBoundingBox(startPos, endPos)

        val stateIntArray = compound.getIntArray(tagStateIntArray)
        val posIntArray = compound.getIntArray(tagPosIntArray)
        val fixedIntArray = posIntArray.asSequence()
                .map { UtilBlockPos.relativeIntToPosition(startPos, it) }
                .map { UtilBlockPos.relativePositionToInt(boundingBox.lowerCorner, it) }
                .toList() // Cant convert to array directly
                .toIntArray()

        val blockMapIntState = BlockMapIntState(intStateMap)
        blockMapIntState.generateStackMapFromStateMap(player)

        return BlockTemplate(UUID.randomUUID(), blockMapIntState, fixedIntArray, stateIntArray, boundingBox, 0, player.name)
    }

    private fun posFromNbtTag(tagCompound: NBTTagCompound, tagName: String): BlockPos {
        val posTag = tagCompound.getCompoundTag(tagName)
        return if (posTag == NBTTagCompound()) BlockPos.ORIGIN else NBTUtil.getPosFromTag(posTag)
    }


}