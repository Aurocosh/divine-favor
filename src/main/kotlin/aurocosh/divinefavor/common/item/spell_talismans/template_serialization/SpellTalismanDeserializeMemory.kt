package aurocosh.divinefavor.common.item.spell_talismans.template_serialization

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block_templates.BlockTemplateCompatibilitySerializer
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.spell_talismans.context.playerField
import aurocosh.divinefavor.common.item.spell_talismans.context.worldField
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.sendStatusMessage
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilStatus.formatString
import aurocosh.divinefavor.common.util.UtilTemplate
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.JsonToNBT
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import java.util.*

class SpellTalismanDeserializeMemory(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    override fun performActionClient(context: CastContext) {
        val (player, world) = context.get(playerField, worldField)

        val clipString = GuiScreen.getClipboardString()
        val canBeLink = possibleLinkStarts.any { clipString.startsWith(it) }
        if (canBeLink) {
            player.sendStatusMessage("message.divinefavor.pastefailed.linkcopied", formatString(TextFormatting.RED))
            return
        }
        deserialize(clipString, player, world)
    }

    fun deserialize(clipString: String, player: EntityPlayer, world: World) {
        try {
            //Anything larger than below is likely to overflow the max packet size, crashing your client.
            val compound = JsonToNBT.getTagFromJson(clipString)
            val template = BlockTemplateCompatibilitySerializer.deserialize(compound, player)
            world[TemplateData][template.uuid] = template
            UtilTemplate.setCurrent(player, template)

            player.sendStatusMessage("message.divinefavor.pastesuccess", formatString(TextFormatting.AQUA))
        } catch (t: Throwable) {
            DivineFavor.logger.error(t)
            player.sendStatusMessage("message.divinefavor.pastefailed", formatString(TextFormatting.RED))
        }
    }

    companion object {
        private val possibleLinkStarts = listOf("http", "www")
    }
}
