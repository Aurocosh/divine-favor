package aurocosh.divinefavor.client.gui.elements

import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import java.awt.Color

class GuiEnumSlider(
        xGlob: Int, yGlob: Int, width: Int, height: Int, displayKey: String,
        override val tooltipKey: String, currentValue: Int, defaultValue: Int, val property: StackPropertyEnum<*>,
        minVal: Int, maxVal: Int, showDec: Boolean, drawStr: Boolean, color: Color,
        par: ISlider, increment: (GuiSliderInt) -> Unit = { it.value++ }, decrement: (GuiSliderInt) -> Unit = { it.value-- }
) : GuiSliderInt(xGlob, yGlob, width, height, displayKey,
        tooltipKey, currentValue, defaultValue,
        minVal, maxVal, showDec, drawStr, color,
        par, increment, decrement) {
    override fun renderText(mc: Minecraft, component: GuiButton, text: String) {
        val color = if (!enabled) 10526880 else if (hovered) 16777120 else -1

        var buttonText = if (text.length != 1) {
            val ordinal = value
            property.toLocalString(ordinal)
        } else
            text

        val strWidth = mc.fontRenderer.getStringWidth(buttonText)
        val ellipsisWidth = mc.fontRenderer.getStringWidth("...")
        if (strWidth > component.width - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, component.width - 6 - ellipsisWidth).trim { it <= ' ' } + "..."

        drawCenteredString(mc.fontRenderer, buttonText, component.x + component.width / 2, component.y + (component.height - 8) / 2, color)
    }
}
