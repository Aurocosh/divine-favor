package aurocosh.divinefavor.common.block_templates

import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.lib.interfaces.INbtWriter
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import net.minecraft.nbt.NBTTagCompound
import java.util.*

object BlockTemplateSerializer : INbtWriter<BlockTemplate> {
    private const val tagUUID = "UUID"

    private const val tagMapIntState = "mapIntState"
    private const val tagMapIntStack = "mapIntStack"
    private const val tagPosIntArray = "posIntArray"
    private const val tagStateIntArray = "stateIntArray"

    private const val tagLowerCorner = "lowerCorner"
    private const val tagUpperCorner = "upperCorner"

    private const val tagDimension = "dimension"
    private const val tagCreator = "creator"

    override fun serialize(nbt: NBTTagCompound, tag: String, instance: BlockTemplate) = nbt.setTag(tag, serialize(instance))
    override fun deserialize(nbt: NBTTagCompound, tag: String) = deserialize(nbt.getCompoundTag(tag))

    fun serialize(template: BlockTemplate): NBTTagCompound {
        val compound = NBTTagCompound()

        compound.setUniqueId(tagUUID, template.uuid)

        compound.setMap(tagMapIntState, template.blockMapIntState.intStateMap, NBTTagCompound::setShort, NBTTagCompound::setBlockState)
        compound.setMap(tagMapIntStack, template.blockMapIntState.intStackMap, NBTTagCompound::setBlockState, NBTTagCompound::setMetaItem)

        compound.setIntArray(tagPosIntArray, template.posIntArray)
        compound.setIntArray(tagStateIntArray, template.stateIntArray)

        compound.setBlockPos(tagLowerCorner, template.boundingBox.lowerCorner)
        compound.setBlockPos(tagUpperCorner, template.boundingBox.upperCorner)
        compound.setInteger(tagDimension, template.dimension)
        compound.setString(tagCreator, template.creator)

        return compound
    }

    fun deserialize(compound: NBTTagCompound): BlockTemplate {
        val uuid = compound.getUniqueId(tagUUID) ?: UUID.randomUUID()

        val intStateMap = compound.getMap(tagMapIntState, NBTTagCompound::getShort, NBTTagCompound::getBlockState)
        val intStackMap = compound.getMap(tagMapIntStack, NBTTagCompound::getBlockState, NBTTagCompound::getMetaItem)

        val posIntArray = compound.getIntArray(tagPosIntArray)
        val stateIntArray = compound.getIntArray(tagStateIntArray)

        val blockMapIntState = BlockMapIntState(intStateMap, intStackMap)

        val lowerCorner = compound.getBlockPos(tagLowerCorner)
        val upperCorner = compound.getBlockPos(tagUpperCorner)
        val dimension = compound.getInteger(tagDimension)
        val creator = compound.getString(tagCreator)

        return BlockTemplate(uuid, blockMapIntState, posIntArray, stateIntArray, CuboidBoundingBox(lowerCorner, upperCorner), dimension, creator)
    }
}