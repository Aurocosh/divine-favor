package aurocosh.divinefavor.client.gui.elements

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.IScrollable
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.lib.TooltipData
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.SoundHandler
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.resources.I18n
import net.minecraft.init.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.client.config.GuiSlider

import java.awt.Color
import java.awt.Rectangle

class GuiSliderInt(
        val xGlob: Int, val yGlob: Int, width: Int, height: Int, val displayKey: String,
        override val tooltipKey: String, val minVal: Int, val maxVal: Int,
        value: Int, showDec: Boolean, drawStr: Boolean, color: Color,
        par: ISlider,
        increment: (GuiSliderInt) -> Unit = { it.value++ }, decrement: (GuiSliderInt) -> Unit = { it.value-- })
    : GuiSlider(0, xGlob + height, yGlob, width - 2 * height, height, "", "", minVal.toDouble(), maxVal.toDouble(), value.toDouble(), showDec, drawStr, par), IButtonContainer, ITooltipProvider, IScrollable {

    private val colorBackground: Int = color.rgb
    private val colorSliderBackground: Int = color.darker().rgb
    private val colorSlider: Int = color.brighter().brighter().rgb

    override val rect: Rectangle = Rectangle(xGlob, yGlob, width, height)

    private val decButton = GuiButtonSliderControl(this, this.x - height, this.y, height, height, "-", decrement)
    private val incButton = GuiButtonSliderControl(this, this.x + this.width, this.y, height, height, "+", increment)

    override val components: List<GuiButton> = listOf(this, decButton, incButton)

    init {
        precision = 1
    }

    var value: Int = value
        set(value) {
            val valueNew = MathHelper.clamp(value, minVal, maxVal)
            if (valueNew == field)
                return
            field = valueNew
            setValue(valueNew.toDouble())
            updateSlider()
        }

    override fun mouseReleased(mouseX: Int, mouseY: Int) {
        super.mouseReleased(mouseX, mouseY)
        setValue(valueInt.toDouble())
    }

    override fun updateSlider() {
        super.updateSlider()
        val valueInt = valueInt
        if (value != valueInt) {
            value = valueInt
            DivineFavor.proxy.clientPlayer.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.02f, 1f)
        }
    }

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partial: Float) {
        if (!visible)
            return

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height
        Gui.drawRect(x, y, x + width, y + height, colorBackground)
        mouseDragged(mc, mouseX, mouseY)
        renderText(mc, this)
    }

    private fun renderText(mc: Minecraft, component: GuiButton) {
        val color = if (!enabled) 10526880 else if (hovered) 16777120 else -1

        var buttonText = I18n.format(displayKey, value)
        val strWidth = mc.fontRenderer.getStringWidth(buttonText)
        val ellipsisWidth = mc.fontRenderer.getStringWidth("...")
        if (strWidth > component.width - 6 && strWidth > ellipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, component.width - 6 - ellipsisWidth).trim { it <= ' ' } + "..."

        drawCenteredString(mc.fontRenderer, buttonText, component.x + component.width / 2, component.y + (component.height - 8) / 2, color)
    }

    override fun playPressSound(soundHandlerIn: SoundHandler) {}

    override fun mouseDragged(mc: Minecraft?, mouseX: Int, mouseY: Int) {
        if (!visible)
            return

        if (dragging) {
            sliderValue = ((mouseX - (x + 4)) / (width - 8).toFloat()).toDouble()
            updateSlider()
        }
        drawBorderedRect(x + (sliderValue * (width - 8)).toInt(), y, 8, height)
    }

    private fun drawBorderedRect(x: Int, y: Int, width: Int, height: Int) {
        Gui.drawRect(x, y, x + width, y + height, colorSliderBackground)
        Gui.drawRect(x + 1, y + 1, x + width - 2, y + height - 2, colorSlider)
    }

    override fun getTooltipData(): TooltipData {
        return TooltipData(tooltipKey, false, xGlob, yGlob)
    }

    private class GuiButtonSliderControl(private val parent: GuiSliderInt, x: Int, y: Int, width: Int, height: Int, buttonText: String, private val action: (GuiSliderInt) -> Unit) : GuiButton(0, x, y, width, height, buttonText) {

        override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partial: Float) {
            if (!visible)
                return

            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height
            Gui.drawRect(x, y, x + width, y + height, parent.colorBackground)
            parent.drawBorderedRect(x, y, width, height)
            parent.renderText(mc, this)
        }

        override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
            if (!super.mousePressed(mc, mouseX, mouseY))
                return false
            action.invoke(parent)
            return true
        }
    }

    override fun scroll(value: Int) {
        this.value += value
    }
}
