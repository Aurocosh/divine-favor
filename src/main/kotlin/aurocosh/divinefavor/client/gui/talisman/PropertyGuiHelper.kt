package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.client.gui.elements.GuiButtonCustomToggle
import aurocosh.divinefavor.client.gui.elements.GuiSliderInt
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyBool
import aurocosh.divinefavor.common.talisman_properties.TalismanPropertyInt
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiSlider
import java.awt.Color

object PropertyGuiHelper {
    fun addNewSlider(property: TalismanPropertyInt, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiSliderInt {
        val valueChangedAction = GuiSlider.ISlider {
            val value = it.valueInt
            property.setValueAndSync(stack, value)
            selector.invoke()
        }

        val value = property.getValue(stack)
        return GuiSliderInt(
                x, y, width, height,
                property.displayKey, property.tooltipKey, property.minValue, property.maxValue,
                value, false, true, Color.DARK_GRAY,
                valueChangedAction)
    }

    fun getToggle(property: TalismanPropertyBool, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiButtonCustomToggle {
        val value = property.getValue(stack)
        val toggleAction: (Boolean) -> Unit = {
            property.setValueAndSync(stack, it)
            selector.invoke();
        }

        return GuiButtonCustomToggle(x, y, width, height, value, property.displayKey, property.tooltipKey, Color.DARK_GRAY, toggleAction)
    }
}