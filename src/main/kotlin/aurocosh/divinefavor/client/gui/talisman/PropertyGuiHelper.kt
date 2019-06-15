package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.client.gui.elements.GuiButtonCustomToggle
import aurocosh.divinefavor.client.gui.elements.GuiSliderInt
import aurocosh.divinefavor.common.stack_properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiSlider
import java.awt.Color

object PropertyGuiHelper {
    fun addNewSlider(property: StackPropertyInt, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiSliderInt {
        val valueChangedAction = GuiSlider.ISlider {
            val value = it.valueInt
            property.setValue(stack, value, true)
            selector.invoke()
        }

        val value = property.getValue(stack)
        return GuiSliderInt(
                x, y, width, height,
                property.displayKey, property.tooltipKey, value, property.defaultValue,
                property.minValue, property.maxValue,
                false, true, Color.DARK_GRAY,
                valueChangedAction)
    }

    fun getToggle(property: StackPropertyBool, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiButtonCustomToggle {
        val value = property.getValue(stack)
        val toggleAction: (Boolean) -> Unit = {
            property.setValue(stack, it, true)
            selector.invoke();
        }

        return GuiButtonCustomToggle(x, y, width, height, value, property.defaultValue, property.displayKey, property.tooltipKey, Color.DARK_GRAY, toggleAction)
    }
}