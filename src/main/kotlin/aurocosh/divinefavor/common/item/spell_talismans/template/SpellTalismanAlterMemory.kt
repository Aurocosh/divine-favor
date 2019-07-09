package aurocosh.divinefavor.common.item.spell_talismans.template

import aurocosh.divinefavor.client.block_ovelay.BlockTemplateRendering
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlockPos
import aurocosh.divinefavor.common.util.UtilTemplate
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanAlterMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    protected val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)

    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    val resetAction = actionHandler.registerAction("reset", serverAction = this::reset)
    val mirrorYAction = actionHandler.registerAction("mirrorY", serverAction = this::mirrorY)

    fun reset(player: EntityPlayer, stack: ItemStack) {

    }

    fun mirrorY(player: EntityPlayer, stack: ItemStack) {
        val currentTemplate = player.divinePlayerData.templateData.currentTemplate
        val templateSavedData = player.world[TemplateData]
        val blockTemplate = templateSavedData[currentTemplate] ?: return

        val newOrientation = blockTemplate.orientation.mirror(EnumFacing.Axis.Y)
        val variation = templateSavedData.getVariation(blockTemplate.uuid, newOrientation)
        if (variation != null) {
            UtilTemplate.setCurrent(player, variation)
            return
        }

        val offsets = blockTemplate.posIntArray.map(UtilBlockPos::intToBlockPos)
        val maxY = blockTemplate.boundingBox.sizeY - 1
        val flippedOffsets = offsets.map { flipY(it, maxY) }

        val newPosIntArray = flippedOffsets.map(UtilBlockPos::blockPosToInt).toIntArray()

        val uuid = UUID.randomUUID()

        val template = BlockTemplate(uuid, blockTemplate.blockMapIntState, newPosIntArray, blockTemplate.stateIntArray, blockTemplate.boundingBox, blockTemplate.dimension, player.name, newOrientation)
        templateSavedData[uuid] = template
        UtilTemplate.setCurrent(player, template)
        templateSavedData.addVariation(blockTemplate.uuid, uuid, newOrientation)
    }

    fun flipY(offset: BlockPos, sizeY: Int): BlockPos {
        return BlockPos(offset.x, sizeY - offset.y, offset.z)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val player = context.player
        val uuid = player.divinePlayerData.templateData.currentTemplate
        val position = positionPropertyWrapper.getPosition(context)
        BlockTemplateRendering.render(lastEvent, player, uuid, position)
    }
}
