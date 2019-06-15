package aurocosh.divinefavor.client.gui.elements

import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.IScrollable
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.constants.ConstLang
import aurocosh.divinefavor.common.lib.TooltipData
import aurocosh.divinefavor.common.lib.extensions.isEven
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.resources.I18n
import java.awt.Color
import java.awt.Rectangle

class GuiButtonCustomToggle(x: Int, y: Int, width: Int, height: Int, value: Boolean, val displayKey: String, override val tooltipKey: String, color: Color, private val toggleAction: (Boolean) -> Unit) : GuiButton(0, x, y, width, height, ""), IButtonContainer, ITooltipProvider, IScrollable {

    private val margin: Int = 1
    private val colorBackground: Int = color.rgb
    private val colorToggle: Int = color.darker().rgb
    private val colorToggleOn: Int = Color.GREEN.darker().rgb
    private val colorToggleOff: Int = color.brighter().brighter().rgb

    override val fastScrollValue = 1
    override val rect = Rectangle(x, y, width, height)

    var toggleState: Boolean = value
        set(value) {
            if (field == value)
                return
            field = value
            toggleAction.invoke(value)
        }

    override val components = listOf(this)

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partial: Float) {
        if (!visible)
            return

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height
        Gui.drawRect(x, y, x + width, y + height, colorBackground)
        Gui.drawRect(x + width - height, y, x + width, y + height, colorToggle)

        val toggledColor = if (toggleState) colorToggleOn else colorToggleOff;
        Gui.drawRect(x + width - height + margin, y + margin, x + width - margin - 1, y + height - margin - 1, toggledColor)

        mouseDragged(mc, mouseX, mouseY)
        renderText(mc, this)
    }


    private fun renderText(mc: Minecraft, component: GuiButton) {
        val color = if (!enabled) 10526880 else if (hovered) 16777120 else -1

        val valueKey = if (toggleState) ConstLang.yesKey else ConstLang.noKey
        var valueText = I18n.format(valueKey)
        var buttonText = I18n.format(displayKey, valueText)

        val strWidth = mc.fontRenderer.getStringWidth(buttonText)
        val ellipsisWidth = mc.fontRenderer.getStringWidth("...")
        if (strWidth > component.width - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, component.width - 6 - ellipsisWidth).trim { it <= ' ' } + "..."

        drawCenteredString(mc.fontRenderer, buttonText, component.x + component.width / 2, component.y + (component.height - 8) / 2, color)
    }

    override fun getTooltipData(): TooltipData {
        return TooltipData(tooltipKey, false, x, y)
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        if (isMouseOver)
            toggleState = !toggleState
        return super.mousePressed(mc, mouseX, mouseY)
    }

    override fun scroll(value: Int) {
        if (value.isEven())
            return
        toggleState = !toggleState
    }
}