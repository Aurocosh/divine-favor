package aurocosh.divinefavor.client.gui.elements

import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.lib.TooltipData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import java.awt.Color

class GuiButtonCustomToggle(val xGlob: Int, val yGlob: Int, width: Int, height: Int, value: Boolean, buttonText: String, override val tooltipKey: String, color: Color, private val toggleAction: (Boolean) -> Unit) : GuiButton(0, xGlob - height, yGlob, width, height, buttonText), IButtonContainer, ITooltipProvider {

    private val colorBackground: Int = color.rgb
    private val colorToggle: Int = color.darker().rgb
    private val colorToggleOn: Int = Color.GREEN.darker().rgb
    private val colorToggleOff: Int = color.brighter().brighter().rgb
    private val margin: Int = 1

    var toggleState: Boolean = value

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
        var buttonText = component.displayString
        val strWidth = mc.fontRenderer.getStringWidth(buttonText)
        val ellipsisWidth = mc.fontRenderer.getStringWidth("...")
        if (strWidth > component.width - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, component.width - 6 - ellipsisWidth).trim { it <= ' ' } + "..."

        drawCenteredString(mc.fontRenderer, buttonText, component.x + component.width / 2, component.y + (component.height - 8) / 2, color)
    }

    //    }
//        return true
//        action.invoke(parent)
//            return false
//        if (!super.mousePressed(mc, mouseX, mouseY))
//    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
//
    override fun getTooltipData(): TooltipData {
        return TooltipData(tooltipKey, false, xGlob, yGlob)
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        if(isMouseOver){
            toggleState = !toggleState
            toggleAction.invoke(toggleState)
        }
        return super.mousePressed(mc, mouseX, mouseY)
    }
}