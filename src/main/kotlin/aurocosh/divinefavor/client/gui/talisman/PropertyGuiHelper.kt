package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.gui.elements.GuiButtonCustomToggle
import aurocosh.divinefavor.client.gui.elements.GuiEnumSlider
import aurocosh.divinefavor.client.gui.elements.GuiSliderInt
import aurocosh.divinefavor.client.gui.elements.GuiStackActionButton
import aurocosh.divinefavor.common.stack_actions.StackAction
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiSlider
import java.awt.Color

object PropertyGuiHelper {
    fun getSlider(property: StackPropertyInt, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiSliderInt {
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

    fun getEnumSlider(property: StackPropertyEnum<*>, stack: ItemStack, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiEnumSlider {
        val valueChangedAction = GuiSlider.ISlider {
            val value = it.valueInt
            property.setOrdinalValue(stack, value, true)
            selector.invoke()
        }

        val value = property.getValue(stack).ordinal
        return GuiEnumSlider(
                x, y, width, height,
                property.displayKey, property.tooltipKey, value, property.defaultValue.ordinal, property,
                0, property.getMaxOrdinal(),
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

    fun getActionButton(action: StackAction, stack: ItemStack, x: Int, y: Int, width: Int, height: Int): GuiStackActionButton {
        val player = DivineFavor.proxy.clientPlayer
        val toggleAction: () -> Unit = {
            action.activateClient(player, stack)
        }

        return GuiStackActionButton(x, y, width, height, action.displayKey, action.tooltipKey, Color.DARK_GRAY, toggleAction)
    }
}