package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.client.block_ovelay.BlockHighlightRendering
import aurocosh.divinefavor.client.block_ovelay.BoxRendering
import aurocosh.divinefavor.client.block_ovelay.MetaItem
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.ItemMemoryDrop
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.ContextProperty
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.network.message.client.MessageSendBlockTemplate
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilBlockState
import aurocosh.divinefavor.common.util.UtilPlayer
import com.google.common.collect.HashMultiset
import net.minecraft.block.Block
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
import javax.vecmath.Color3f

abstract class SpellTalismanCopy(name: String, spirit: ModSpirit, favorCost: Int = ConfigGeneral.blockBuildingCost) : ItemSpellTalisman(name, spirit, favorCost, SpellOptions.TRACE_ONLY_CAST) {
    protected val finalCoordinates = ContextProperty<List<BlockPos>>("coordinates", emptyList())

    override fun validate(context: TalismanContext): Boolean {
        val coordinates = context.get(finalCoordinates)
        val cubeCoordinates = CuboidBoundingBox.getBoundingBox(coordinates)

        val (player, world) = context.get(playerField, worldField)

        val maxSize: Int = sequenceOf(cubeCoordinates.sizeX, cubeCoordinates.sizeY, cubeCoordinates.sizeZ).max() as Int
        if (maxSize > 125) {
            player.sendStatusMessage(TextComponentString(TextFormatting.RED.toString() + TextComponentTranslation("message.divinefavor:copy_area_is_too_big", 125).unformattedComponentText), true)
            return false
        }


        return true
    }

    override fun preProcess(context: TalismanContext): Boolean {
        val coordinates = getFinalCoordinates(context)
        context.set(finalCoordinates, coordinates)
        return coordinates.isNotEmpty()
    }

    protected open fun getCommonCoordinates(context: TalismanContext): List<BlockPos> {
        val world = context.world
        val coordinates = getCoordinates(context)
        return coordinates.S.filterNot(world::isAirBlock).toList()
    }

    protected open fun getRenderCoordinates(context: TalismanContext): List<BlockPos> = getCommonCoordinates(context)
    protected open fun getFinalCoordinates(context: TalismanContext): List<BlockPos> = getCommonCoordinates(context)

    override fun performActionServer(context: TalismanContext) {
        val coordinates = context.get(finalCoordinates)
        copyBlocks(context.world, context.player, coordinates)
    }

    private fun copyBlocks(world: World, player: EntityPlayer, coordinates: List<BlockPos>): Boolean {
        val cubeCoordinates = CuboidBoundingBox.getBoundingBox(coordinates)
        val origin = cubeCoordinates.lowerCorner

        val tileEntityCount = coordinates.S
                .filter(world::hasTileEntity)
                .count()

        val validCoordinates = coordinates.S
                .filterNot(world::isAirBlock)
                .filterNot(world::hasTileEntity)
                .filterNot(world::getBlock, Block::isLiquid)

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
                val uniqueItem = UtilBlockState.blockStateToUniqueItem(actualState, player, pos)
                if (uniqueItem.item !== Items.AIR) {
                    posIntArrayList.add(UtilBlockPos.relativePositionToInt(origin, pos))
                    blockMapIntState.addToMap(actualState)
                    stateIntArrayList.add(blockMapIntState.findSlot(actualState).toInt())

                    blockMapIntState.addToStackMap(uniqueItem, actualState)
                    blockCount++
                    if (blockCount > 32768) {
                        player.sendStatusMessage(TextComponentString(TextFormatting.RED.toString() + TextComponentTranslation("message.gadget.toomanyblocks").unformattedComponentText), true)
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
        val blockTemplate = BlockTemplate(uuid, blockMapIntState, posIntArrayList.toIntArray(), stateIntArrayList.toIntArray(), cubeCoordinates, player.dimension, player.name)

        val templateSavedData = world[TemplateData]
        templateSavedData[uuid] = blockTemplate

        val stack = ItemStack(ModItems.memory_drop)
        stack.set(ItemMemoryDrop.uuid, uuid)
        UtilPlayer.addStackToInventoryOrDrop(player, stack)

        MessageSendBlockTemplate(uuid, blockTemplate)

        if (tileEntityCount > 0) {
            player.sendStatusMessage(TextComponentString(TextFormatting.YELLOW.toString() + TextComponentTranslation("message.gadget.TEinCopy").unformattedComponentText + ": " + tileEntityCount), true)
        } else {
            player.sendStatusMessage(TextComponentString(TextFormatting.AQUA.toString() + TextComponentTranslation("message.gadget.copied").unformattedComponentText), true)
        }
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val player = context.player
        val coordinates = getRenderCoordinates(context)
        BlockHighlightRendering.render(lastEvent, player, coordinates, Color3f(0.3f, 0.3f, 0f))

        val cubeCoordinates = CuboidBoundingBox.getBoundingBox(coordinates)
        BoxRendering.render(lastEvent, player, cubeCoordinates.lowerCorner, cubeCoordinates.upperCorner)
    }

    protected abstract fun getCoordinates(context: TalismanContext): List<BlockPos>


}
