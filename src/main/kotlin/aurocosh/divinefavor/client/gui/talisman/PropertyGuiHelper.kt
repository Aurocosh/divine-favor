package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.client.gui.elements.GuiButtonCustomToggle
import aurocosh.divinefavor.client.gui.elements.GuiSliderInt
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.config.GuiSlider
import java.awt.Color

object PropertyGuiHelper {
    fun addNewSlider(property: TalismanPropertyInt, stack: ItemStack, playerSlot: Int, x: Int, y: Int, width: Int, height: Int, selector: () -> Unit): GuiSliderInt {
        val valueChangedAction = GuiSlider.ISlider {
            val value = it.valueInt
            if(property.setValue(stack, value))
                MessageSyncTalismanPropertyInt(playerSlot, property.name, value).send()
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
            if(property.setValue(stack, it))
                MessageSyncTalismanPropertyBool(playerSlot, property.name, it).send()
            selector.invoke();
        }

        return GuiButtonCustomToggle(x, y, width, height, value, property.prefix, property.tooltipKey, Color.DARK_GRAY, toggleAction)
    }
}