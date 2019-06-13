package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.client.gui.elements.GuiButtonCustomToggle
import aurocosh.divinefavor.client.gui.elements.GuiSliderInt
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiSlider
import java.awt.Color

object PropertyGuiHelper {
    fun addNewSlider(property: TalismanPropertyInt, stack: ItemStack, playerSlot: Int, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiSliderInt {
        val valueChangedAction = GuiSlider.ISlider {
            property.setValueAndSync(stack, it.valueInt, playerSlot)
            selector.invoke()
        }

        val value = property.getValue(stack)
        return GuiSliderInt(
                x, y, width, height,
                property.prefix + ": ", " " + property.suffix, property.tooltipKey, property.minValue, property.maxValue,
                value, false, true, Color.DARK_GRAY,
                valueChangedAction)
    }

    fun getToggle(property: TalismanPropertyBool, stack: ItemStack, playerSlot: Int, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiButtonCustomToggle {
        val value = property.getValue(stack)
        val toggleAction: (Boolean) -> Unit = {
            property.setValueAndSync(stack, it, playerSlot)
            selector.invoke();
        }

        return GuiButtonCustomToggle(x, y, width, height, value, property.prefix, property.tooltipKey, Color.DARK_GRAY, toggleAction)
    }
}