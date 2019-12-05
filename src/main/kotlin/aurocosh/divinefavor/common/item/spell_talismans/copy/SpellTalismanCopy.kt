package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.client.block_ovelay.BoxRendering
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.network.TemplateNetHandlers
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilBlockState
import aurocosh.divinefavor.common.util.UtilTemplate
import com.google.common.collect.HashMultiset
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*
import javax.vecmath.Color4f

abstract class SpellTalismanCopy(name: String, spirit: ModSpirit, favorCost: Int = ConfigGeneral.blockBuildingCost) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.ALL_CAST_TRACE) {
    protected data class CopyCoordinates(val coordinates: List<BlockPos>, val boundingBox: CuboidBoundingBox)

    protected val finalCoordinates = ContextProperty("coordinates", CopyCoordinates(emptyList(), CuboidBoundingBox()))

    override fun validate(context: TalismanContext): Boolean {
        val boundingBox = context.get(finalCoordinates).boundingBox
        val maxSize: Int = sequenceOf(boundingBox.sizeX, boundingBox.sizeY, boundingBox.sizeZ).max() as Int
        if (maxSize > 125) {
            context.player.sendStatusMessage(TextComponentString(TextFormatting.RED.toString() + TextComponentTranslation("message.divinefavor:copy_area_is_too_big", 125).unformattedComponentText), true)
            return false
        }

        return true
    }

    override fun preProcess(context: TalismanContext): Boolean {
        val coordinates = getCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.coordinates.isNotEmpty()
    }

    override fun performActionServer(context: TalismanContext) {
        val coordinates = context.get(finalCoordinates).coordinates
        copyBlocks(context.world, context.player, coordinates)
    }

    private fun copyBlocks(world: World, player: EntityPlayer, coordinates: List<BlockPos>): Boolean {
        val boundingBox = CuboidBoundingBox.getBoundingBox(coordinates)
        val relativeOrigin = boundingBox.lowerCorner

        val tileEntityCount = coordinates.S
                .filter(world::hasTileEntity)
                .count()

        val validCoordinates = coordinates.S
                .filterNot(world::isAirBlock)
                .filterNot(world::hasTileEntity)
                .filterNot(world::getBlockState, IBlockState::isLiquid)

        val posIntArrayList = ArrayList<Int>()
        val stateIntArrayList = ArrayList<Int>()
        val blockMapIntState = BlockMapIntState()
        val itemCountMap = HashMultiset.create<MetaItem>()

        var blockCount = 0
        for (pos in validCoordinates) {
            val tempState = world.getBlockState(pos)
            val assignState = UtilBlockState.getSpecificStates(tempState, world, player, pos, true)
            val actualState: IBlockState? = assignState.getActualState(world, pos)
            if (actualState != null) {
                val relativePos = pos.subtract(relativeOrigin)
                val uniqueItem = UtilBlockState.blockStateToUniqueItem(actualState, player, relativePos)
                if (uniqueItem.item !== Items.AIR) {
                    posIntArrayList.add(UtilBlockPos.blockPosToInt(relativePos))
                    blockMapIntState.addToMap(actualState)
                    stateIntArrayList.add(blockMapIntState.findSlot(actualState).toInt())

                    blockMapIntState.addToStackMap(uniqueItem, actualState)
                    blockCount++
                    if (blockCount > 32768) {
                        player.sendStatusMessage(TextComponentString(TextFormatting.RED.toString() + TextComponentTranslation("message.divinefavor.toomanyblocks").unformattedComponentText), true)
                        return false
                    }
                    val drops = NonNullList.create<ItemStack>()
                    actualState.block.getDrops(drops, world, BlockPos(0, 0, 0), actualState, 0)

                    var neededItems = 0
                    for (drop in drops) {
                        if (drop.item == uniqueItem.item) {
                            neededItems++
                        }
                    }
                    if (neededItems == 0) {
                        neededItems = 1
                    }
                    itemCountMap.add(uniqueItem, neededItems)
                }
            }
        }

        val uuid = UUID.randomUUID()
        val blockTemplate = BlockTemplate(uuid, blockMapIntState, posIntArrayList.toIntArray(), stateIntArrayList.toIntArray(), boundingBox.moveToOrigin(), player.dimension, player.name)

        val templateSavedData = world[TemplateData]
        templateSavedData[uuid] = blockTemplate
        TemplateNetHandlers.clientHandler.send(player, blockTemplate)
        UtilTemplate.setCurrent(player, uuid)

//        val stack = ItemStack(ModItems.memory_drop)
//        stack.set(ItemMemoryDrop.uuid, uuid)
//        UtilPlayer.addStackToInventoryOrDrop(player, stack)


        if (tileEntityCount > 0) {
            player.sendStatusMessage(TextComponentString(TextFormatting.YELLOW.toString() + TextComponentTranslation("message.divinefavor.TEinCopy").unformattedComponentText + ": " + tileEntityCount), true)
        } else {
            player.sendStatusMessage(TextComponentString(TextFormatting.AQUA.toString() + TextComponentTranslation("message.divinefavor.copied").unformattedComponentText), true)
        }
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val player = context.player
        val (coordinates, boundingBox) = getCoordinates(context)
        BlockHighlightRendering.render(lastEvent, player, coordinates, Color4f(0.3f, 0.3f, 0f, 0.3f))

        BoxRendering.render(lastEvent, player, boundingBox.lowerCorner, boundingBox.upperCorner)
    }

    protected abstract fun getCoordinates(context: TalismanContext): CopyCoordinates


}
