package aurocosh.divinefavor.common.item.spell_talismans.template_serialization

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.block_templates.BlockTemplateCompatibilitySerializer
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.sendStatusMessage
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilSerialize
import aurocosh.divinefavor.common.util.UtilStatus.formatString
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.TextFormatting
import java.io.IOException
import java.util.*

class SpellTalismanSerializeMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun performActionClient(context: TalismanContext) {
        val (player, world) = context.get(playerField, worldField)
        val currentTemplate = player.divinePlayerData.templateData.currentTemplate
        val blockTemplate = world[TemplateData][currentTemplate] ?: return
        copyTemplate(player, blockTemplate)
    }

    private fun copyTemplate(player: EntityPlayer, template: BlockTemplate) {
        val compound = BlockTemplateCompatibilitySerializer.serialize(template)
        try {
            if (UtilSerialize.getPasteStream(compound) != null) {
                val jsonTag = compound.toString()
                GuiScreen.setClipboardString(jsonTag)
                player.sendStatusMessage("message.divinefavor.copysuccess", formatString(TextFormatting.AQUA));
            } else {
                player.sendStatusMessage("message.divinefavor.pastetoobig", formatString(TextFormatting.RED))
            }
        } catch (e: IOException) {
            DivineFavor.logger.error("Failed to evaluate template network size. Template will be considered too large.", e)
            player.sendStatusMessage("message.divinefavor.pastetoobig", formatString(TextFormatting.RED))
        }
    }
}
