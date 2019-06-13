package aurocosh.divinefavor.client.gui.elements

import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.ItemStackIdComparator
import aurocosh.divinefavor.common.lib.TooltipData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import net.minecraft.item.ItemStack
import java.awt.Color

class GuiButtonStack(x: Int, y: Int, width: Int, height: Int, val stack: ItemStack, color: Color, private val action: () -> Unit) : GuiButton(0, x, y, width, height, ""), IButtonContainer, ITooltipProvider {

    override val tooltipKey = getTooltipFromStack(stack)
    private val colorBackground: Int = color.rgb

    override val components = listOf(this)

    override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partial: Float) {
        if (!visible)
            return

        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height
//        Gui.drawRect(x, y, x + width, y + height, colorBackground)

        mc.renderItem.renderItemIntoGUI(stack, x, y)

        mouseDragged(mc, mouseX, mouseY)
    }

    override fun getTooltipData(): TooltipData {
        return TooltipData(tooltipKey, false, x, y)
    }

    override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
        if (isMouseOver)
            action.invoke()
        return super.mousePressed(mc, mouseX, mouseY)
    }

    private fun getTooltipFromStack(stack: ItemStack): String {
        val item = stack.item
        if (item is ModItem)
            return item.descriptionKey
        else
            return ResourceNamer.getNameString("tooltip", "description_not_found")
    }
}