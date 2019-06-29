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
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class SpellTalismanSerializeMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun performActionServer(context: TalismanContext) {
        val (player, world) = context.get(playerField, worldField)
        val currentTemplate = player.divinePlayerData.templateData.currentTemplate
        val blockTemplate = world[TemplateData][currentTemplate] ?: return
        copyTemplate(player, blockTemplate)
    }

    private fun copyTemplate(player: EntityPlayer, template: BlockTemplate) {
        val compound = BlockTemplateCompatibilitySerializer.serialize(template)
        try {
            if (getPasteStream(compound) != null) {
                val jsonTag = compound.toString()
                GuiScreen.setClipboardString(jsonTag)
                player.sendStatusMessage(TextComponentString(TextFormatting.AQUA.toString() + TextComponentTranslation("message.gadget.copysuccess").unformattedComponentText), false)
            } else {
                pasteIsTooLarge(player)
            }
        } catch (e: IOException) {
            DivineFavor.logger.error("Failed to evaluate template network size. Template will be considered too large.", e)
            pasteIsTooLarge(player)
        }
    }

    @Throws(IOException::class)
    fun getPasteStream(compound: NBTTagCompound): ByteArrayOutputStream? {
        val outputStream = ByteArrayOutputStream()
        CompressedStreamTools.writeCompressed(compound, outputStream)
        return if (outputStream.size() < Short.MAX_VALUE - 200) outputStream else null
    }

    private fun pasteIsTooLarge(player: EntityPlayer) {
        player.sendStatusMessage(TextComponentString(TextFormatting.RED.toString() + TextComponentTranslation("message.gadget.pastetoobig").unformattedComponentText), false)
    }
}
