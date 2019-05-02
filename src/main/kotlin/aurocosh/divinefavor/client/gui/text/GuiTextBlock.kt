package aurocosh.divinefavor.client.gui.text

import aurocosh.divinefavor.client.gui.widgets.GuiWidget
import net.minecraft.client.Minecraft
import java.awt.Color
import java.lang.Integer.min
import java.util.*

class GuiTextBlock(x: Int, y: Int, width: Int, height: Int, text: String) : GuiWidget(x, y, width, height) {
    var description: List<String>? = null
        private set

    init {
        setText(text)
    }

    fun setText(text: String) {
        var descriptionLines = Arrays.asList(text)
        descriptionLines = expandNewlines(descriptionLines)
        descriptionLines = wrapDescriptionLines(descriptionLines)

        val maxLines = height / 20
        this.description = descriptionLines.subList(0, min(descriptionLines.size, maxLines))
    }

    private fun expandNewlines(descriptionLines: List<String>): List<String> {
        val descriptionLinesExpanded = ArrayList<String>()
        for (descriptionLine in descriptionLines) {
            val descriptionLineExpanded = descriptionLine.split("\\\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Collections.addAll(descriptionLinesExpanded, *descriptionLineExpanded)
        }
        return descriptionLinesExpanded
    }

    private fun wrapDescriptionLines(descriptionLines: List<String>): List<String> {
        val minecraft = Minecraft.getMinecraft()
        val descriptionLinesWrapped = ArrayList<String>()
        for (descriptionLine in descriptionLines) {
            val textLines = minecraft.fontRenderer.listFormattedStringToWidth(descriptionLine, width)
            descriptionLinesWrapped.addAll(textLines)
        }
        return descriptionLinesWrapped
    }

    override fun draw(minecraft: Minecraft, guiLeft: Int, guiTop: Int) {
        val xPos = guiLeft + x
        var yPos = guiTop + y + 4

        for (descriptionLine in description!!) {
            minecraft.fontRenderer.drawString(descriptionLine, xPos, yPos, Color.black.rgb)
            yPos += minecraft.fontRenderer.FONT_HEIGHT + lineSpacing
        }
    }

    companion object {
        private val lineSpacing = 2
    }

}
