package aurocosh.divinefavor.common.item.spell_talismans.template

import aurocosh.divinefavor.client.block_ovelay.BlockTemplateRendering
import aurocosh.divinefavor.common.item.spell_talismans.base.CastType
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.common_build_properties.ShiftedPositionPropertyWrapper
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.template.converters.BlockTemplateMirrorConverter
import aurocosh.divinefavor.common.item.spell_talismans.template.converters.BlockTemplateRotationConverter
import aurocosh.divinefavor.common.lib.RotationDirection
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class SpellTalismanAlterMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    protected val positionPropertyWrapper = ShiftedPositionPropertyWrapper(propertyHandler)

    @SideOnly(Side.CLIENT)
    override fun shouldRender(context: TalismanContext): Boolean = positionPropertyWrapper.shouldRender(context)
    override fun raycastBlock(stack: ItemStack, castType: CastType) = positionPropertyWrapper.shouldRaycastBlock(stack)

    init {
        actionHandler.registerAction("mirrorX", 4, 0, 0, 50, 14, serverAction = this::mirrorX)
        actionHandler.registerAction("mirrorY", 4, 50, 0, 50, 14, serverAction = this::mirrorY)
        actionHandler.registerAction("mirrorZ", 4, 100, 0, 50, 14, serverAction = this::mirrorZ)

        actionHandler.registerAction("rotateClockX", 5, 0, 0, 70, 14, serverAction = this::rotateClockX)
        actionHandler.registerAction("rotateContrX", 5, 74, 0, 70, 14, serverAction = this::rotateContrX)
        actionHandler.registerAction("rotateClockY", 6, 0, 0, 70, 14, serverAction = this::rotateClockY)
        actionHandler.registerAction("rotateContrY", 6, 74, 0, 70, 14, serverAction = this::rotateContrY)
        actionHandler.registerAction("rotateClockZ", 7, 0, 0, 70, 14, serverAction = this::rotateClockZ)
        actionHandler.registerAction("rotateContrZ", 7, 74, 0, 70, 14, serverAction = this::rotateContrZ)
    }

    private fun mirrorX(player: EntityPlayer, stack: ItemStack) = BlockTemplateMirrorConverter(EnumFacing.Axis.X).convert(player)
    private fun mirrorY(player: EntityPlayer, stack: ItemStack) = BlockTemplateMirrorConverter(EnumFacing.Axis.Y).convert(player)
    private fun mirrorZ(player: EntityPlayer, stack: ItemStack) = BlockTemplateMirrorConverter(EnumFacing.Axis.Z).convert(player)

    private fun rotateClockX(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.X, RotationDirection.Clockwise).convert(player)
    private fun rotateContrX(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.X, RotationDirection.Counter).convert(player)

    private fun rotateClockY(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.Y, RotationDirection.Clockwise).convert(player)
    private fun rotateContrY(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.Y, RotationDirection.Counter).convert(player)

    private fun rotateClockZ(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.Z, RotationDirection.Clockwise).convert(player)
    private fun rotateContrZ(player: EntityPlayer, stack: ItemStack) = BlockTemplateRotationConverter(EnumFacing.Axis.Z, RotationDirection.Counter).convert(player)

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val player = context.player
        val uuid = player.divinePlayerData.templateData.currentTemplate
        val position = positionPropertyWrapper.getPosition(context)
        BlockTemplateRendering.render(lastEvent, player, uuid, position)
    }
}
