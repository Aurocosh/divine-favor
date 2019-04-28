package aurocosh.divinefavor.common.item.mystic_architect_stick

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.math.CubeCoordinates
import aurocosh.divinefavor.common.muliblock.MultiBlockPart
import aurocosh.divinefavor.common.muliblock.serialization.BlockPosToByteSerializer
import aurocosh.divinefavor.common.muliblock.serialization.MultiBlockData
import aurocosh.divinefavor.common.muliblock.serialization.StateValidatorSerializer
import aurocosh.divinefavor.common.muliblock.validators.AirStateValidator
import aurocosh.divinefavor.common.muliblock.validators.BlockStateValidator
import aurocosh.divinefavor.common.muliblock.validators.StateValidator
import aurocosh.divinefavor.common.util.UtilNbt
import com.google.gson.GsonBuilder
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

import java.util.HashMap

import net.minecraft.client.gui.GuiScreen.setClipboardString

class ItemMysticArchitectStick : ModItem("mystic_architect_stick", "mystic_architect_stick", ConstMainTabOrder.TOOLS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun onItemUse(playerIn: EntityPlayer?, world: World, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        if (!world.isRemote)
            return EnumActionResult.PASS
        if (hand == EnumHand.OFF_HAND)
            return EnumActionResult.PASS

        val stack = playerIn!!.heldItemMainhand
        val compound = UtilNbt.getNbt(stack)

        if (!playerIn.isSneaking) {
            val currentIsSecond = compound.getBoolean(TAG_CURRENT_IS_SECOND)
            if (currentIsSecond)
                UtilNbt.setBlockPos(compound, TAG_POS_SECOND, pos)
            else
                UtilNbt.setBlockPos(compound, TAG_POS_FIRST, pos)
            compound.setBoolean(TAG_CURRENT_IS_SECOND, !currentIsSecond)
            return EnumActionResult.SUCCESS

        } else {
            val mode = compound.getInteger(TAG_CURRENT_MODE)
            val stickMode = ArchitectStickMode.VALUES[mode]

            when (stickMode) {
                ArchitectStickMode.SELECT_MULTIBLOCK_BASE -> {
                    compound.setLong(TAG_BASE_POSITION, pos!!.toLong())
                    DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Set base position: $pos"))
                }
                ArchitectStickMode.SELECT_CONTROLLER -> {
                    compound.setLong(TAG_MEDIUM_POSITION, pos!!.toLong())
                    DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Set medium position: $pos"))
                }
                ArchitectStickMode.SELECT_AIR_BLOCK -> selectAirBlock(world, pos, compound)
                ArchitectStickMode.CREATE_TEMPLATE -> createTemplate(world, pos, stack, compound)
            }
        }
        return EnumActionResult.SUCCESS
    }

    private fun selectAirBlock(worldIn: World, pos: BlockPos?, compound: NBTTagCompound) {
        if (compound.hasKey(TAG_AIR_TYPE)) {
            compound.removeTag(TAG_AIR_TYPE)
            DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Air block marker cleared"))
        } else {
            val block = worldIn.getBlockState(pos!!).block
            val name = block.registryName!!.toString()
            compound.setString(TAG_AIR_TYPE, name)
            DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Air block marker setValue to: $name"))
        }
    }

    private fun createTemplate(worldIn: World, pos: BlockPos?, stack: ItemStack, compound: NBTTagCompound) {
        if (!UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND)) {
            DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Corners not setValue"))
            return
        }
        if (!UtilNbt.checkForTags(stack, TAG_BASE_POSITION)) {
            DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Base position not setValue"))
            return
        }
        if (!UtilNbt.checkForTags(stack, TAG_MEDIUM_POSITION)) {
            DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Medium position not setValue"))
            return
        }

        val firstCorner = UtilNbt.getBlockPos(compound, TAG_POS_FIRST, BlockPos.ORIGIN)
        val secondCorner = UtilNbt.getBlockPos(compound, TAG_POS_SECOND, BlockPos.ORIGIN)
        val basePosition = UtilNbt.getBlockPos(compound, TAG_BASE_POSITION, BlockPos.ORIGIN)
        val mediumPosition = UtilNbt.getBlockPos(compound, TAG_MEDIUM_POSITION, BlockPos.ORIGIN)
        val airMarker = compound.getString(TAG_AIR_TYPE)

        val templateString = getTemplateData(worldIn, firstCorner, secondCorner, mediumPosition, basePosition, airMarker)
        setClipboardString(templateString)

        DivineFavor.proxy.clientPlayer.sendMessage(TextComponentString("Template copied to clipboard"))
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.heldItemMainhand
        if (!worldIn.isRemote)
            return ActionResult(EnumActionResult.PASS, stack)
        if (hand == EnumHand.OFF_HAND)
            return ActionResult(EnumActionResult.PASS, stack)

        val isCornersSet = UtilNbt.checkForTags(stack, TAG_POS_FIRST, TAG_POS_SECOND)
        return if (!isCornersSet) ActionResult(EnumActionResult.PASS, stack) else ActionResult(EnumActionResult.SUCCESS, stack)

    }

    private fun getTemplateData(world: World, firstCorner: BlockPos, secondCorner: BlockPos, controllerPosition: BlockPos, basePosition: BlockPos, airMarker: String): String {
        val coordinatesWorld = CubeCoordinates(firstCorner, secondCorner)
        val positions = coordinatesWorld.allPositionsInside
        val lowerCorner = coordinatesWorld.lowerCorner
        val airMarkerName = ResourceLocation(airMarker)

        val partMap = HashMap<Block, MutableList<BlockPos>>()
        for (pos in positions) {
            val state = world.getBlockState(pos)
            val block = state.block
            if (block === Blocks.AIR)
                continue
            val partPositions: MutableList<BlockPos> = partMap.computeIfAbsent(block) { _ -> ArrayList() }
            partPositions.add(pos.subtract(lowerCorner))
        }

        val parts = ArrayList<MultiBlockPart>(partMap.size)
        for ((block, value) in partMap) {
            val validator: StateValidator
            if (block.registryName == airMarkerName)
                validator = AirStateValidator()
            else
                validator = BlockStateValidator(block.registryName)
            parts.add(MultiBlockPart(validator, value))
        }

        val relativeBase = basePosition.subtract(lowerCorner)
        val relativeController = controllerPosition.subtract(lowerCorner)
        val data = MultiBlockData(false, relativeBase, relativeController, parts)

        val gson = GsonBuilder()
                .registerTypeAdapter(StateValidator::class.java, StateValidatorSerializer())
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(BlockPos::class.java, BlockPosToByteSerializer())
                .create()

        return gson.toJson(data, MultiBlockData::class.java)
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    companion object {
        val TAG_CURRENT_IS_SECOND = "CurrentIsSecond"
        val TAG_POS_FIRST = "PosFirst"
        val TAG_POS_SECOND = "PosSecond"
        val TAG_AIR_TYPE = "AirType"
        val TAG_BASE_POSITION = "BasePosition"
        val TAG_MEDIUM_POSITION = "MediumPosition"
        val TAG_CURRENT_MODE = "CurrentMode"
    }
}
