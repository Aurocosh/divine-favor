package aurocosh.divinefavor.common.item.spell_talismans.template

import aurocosh.divinefavor.client.block_ovelay.BlockTemplateRendering
import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.block_templates.TemplateFinalBlockState
import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanBuildTemplate(name: String, spirit: ModSpirit, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, ConfigGeneral.blockBuildingCost, options) {
    protected val finalTemplate = ContextProperty<List<TemplateFinalBlockState>>("final_template", emptyList())

    val converter: IIndexedEnum<TemplateAnchor> = TemplateAnchor
    protected val anchor = propertyHandler.registerEnumProperty("template_anchor", TemplateAnchor.Center, converter, true)
    protected val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    override fun getApproximateFavorCost(itemStack: ItemStack) = favorCost
    override fun getFinalFavorCost(context: TalismanContext) = favorCost * context.get(finalTemplate).size

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    override fun preProcess(context: TalismanContext): Boolean {
        val (player, world, stack) = context.get(playerField, worldField, stackField)
        if (world.isRemote)
            return false

        val uuid = player.divinePlayerData.templateData.currentTemplate
        val blockTemplate = world[TemplateData][uuid] ?: return false
        val position = positionPropertyWrapper.getPosition(context)
        val anchor = stack.get(anchor)
        val finalPosition = getPosition(position, anchor, blockTemplate.boundingBox)

        val stackMap = blockTemplate.blockMapIntState.intStackMap
        val blockMapList = blockTemplate.getAbsoluteBlockMap(finalPosition)

        val validBlockStates = blockMapList.S
                .filter { UtilBlock.canReplaceBlock(player, world, it.pos) }
                .filter { UtilBlock.isAirOrReplaceable(world, it.pos) }
                .map { stackMap[it.state]?.let { metaItem -> TemplateFinalBlockState(it.pos, it.state, metaItem) } }
                .filterNotNull()
                .toList()

        context.set(finalTemplate, validBlockStates)
        return validBlockStates.isNotEmpty()
    }

    override fun performActionServer(context: TalismanContext) {
        val (player, world) = context.get(playerField, worldField)
        val template = context.get(finalTemplate)
        for (blockState in template)
            buildBlock(player, world, blockState.pos, blockState.state, blockState.metaItem)
    }

    override fun performActionClient(context: TalismanContext) {
        positionPropertyWrapper.isLockPosition.setValue(context.stack, false, true)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val (player, world, stack) = context.get(playerField, worldField, stackField)
        val uuid = player.divinePlayerData.templateData.currentTemplate
        val template = world[TemplateData][uuid] ?: return
        val position = positionPropertyWrapper.getPosition(context)
        val anchor = stack.get(anchor)
        val finalPosition = getPosition(position, anchor, template.boundingBox)
        BlockTemplateRendering.render(lastEvent, player, uuid, finalPosition)
    }

    fun getPosition(pos: BlockPos, anchor: TemplateAnchor, cuboidBoundingBox: CuboidBoundingBox): BlockPos {
        return when (anchor) {
            TemplateAnchor.NorthWest -> pos
            TemplateAnchor.SouthWest -> pos.add(0, 0, -cuboidBoundingBox.sizeZ)
            TemplateAnchor.NorthEast -> pos.add(-cuboidBoundingBox.sizeX, 0, 0)
            TemplateAnchor.SouthEast -> pos.add(-cuboidBoundingBox.sizeX, 0, -cuboidBoundingBox.sizeZ)
            TemplateAnchor.Center -> pos.add(-cuboidBoundingBox.sizeX / 2, 0, -cuboidBoundingBox.sizeZ / 2)
        }
    }

    private fun buildBlock(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, metaItem: MetaItem) {
        val drops = NonNullList.create<ItemStack>()
        state.block.getDrops(drops, world, pos, state, 0)

        val stack = metaItem.toStack()
        val dropCount = drops.filter { it.item == stack.item }.count()
        val itemsToConsume = if (dropCount > 0) dropCount else 1

        if (UtilPlayer.consumeItems(stack, player, itemsToConsume))
            UtilBlock.replaceBlock(player, world, pos, state)
    }
}
