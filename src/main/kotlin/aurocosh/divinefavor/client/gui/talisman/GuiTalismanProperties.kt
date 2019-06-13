package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.item.talisman.properties.TalismanProperty
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.lib.TooltipCache
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiLabel
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.GameSettings
import net.minecraft.item.ItemStack
import java.awt.Color

import java.io.IOException

class GuiTalismanProperties(private val stack: ItemStack, private val playerSlot: Int) : GuiScreen() {
    private var selectedPropertyIndex = -1
    private val properties: List<TalismanProperty<out Any>>
    private var propertyGuiElements: MutableList<IButtonContainer> = ArrayList()

    private val yMargin = 20
    private val xMargin = 20

    private val tooltipWidth = 100
    private val tooltipHeight = 500

    private val elementWidth = 140
    private val elementHeight = 14

    private var nextElementX = xMargin
    private var nextElementY = yMargin

    private var elementStepY = 20
    private val tooltipStepY = 20

    private val tooltipCache = TooltipCache()

    init {
        mc = Minecraft.getMinecraft()
        val item = stack.item as ItemTalisman
        properties = item.properties
    }

    override fun initGui() {
        properties.forEach(this::addPropertyToGui)
    }

    private fun addPropertyToGui(property: TalismanProperty<out Any>) {
        if (property is TalismanPropertyInt) {
            val nextIndex = propertyGuiElements.size
            val selector = { selectedPropertyIndex = nextIndex }
            val slider = PropertyGuiHelper.addNewSlider(property, stack, playerSlot, nextElementX, nextElementY, elementWidth, elementHeight, selector)
            addGuiElement(slider)
        } else if (property is TalismanPropertyBool) {
            val nextIndex = propertyGuiElements.size
            val selector = { selectedPropertyIndex = nextIndex }
            val slider = PropertyGuiHelper.getToggle(property, stack, playerSlot, nextElementX, nextElementY, elementWidth, elementHeight, selector)
            addGuiElement(slider)
        } else
            DivineFavor.logger.error("Attempted to add unknown type of property")
    }

    private fun addGuiElement(element: IButtonContainer) {
        element.components.forEach { this.addButton(it) }
        propertyGuiElements.add(element)
        nextElementY += elementStepY
    }

    override fun drawScreen(mx: Int, my: Int, partialTicks: Float) {
        super.drawScreen(mx, my, partialTicks)
        renderTooltips();
    }

    private fun renderTooltips() {
        val xTool = width - tooltipWidth - xMargin
        val yTool = yMargin

        val tooltipProviders = buttonList.S.filterIsInstance<ITooltipProvider>().filter(ITooltipProvider::isMouseOver)

        for (provider in tooltipProviders) {
            val tooltipData = provider.getTooltipData()
            val lines = tooltipCache.getTooltip(tooltipData, tooltipWidth, tooltipHeight)

            val x = tooltipData.getX(xTool)
            val y = tooltipData.getY(yTool)

            for ((index, line) in lines.withIndex()) {
                val yCoord = (y + tooltipStepY * index).toFloat()
                fontRenderer.drawStringWithShadow(line, x.toFloat(), yCoord, Color.white.rgb)
            }
        }
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)

    }

    override fun updateScreen() {
        if (!GameSettings.isKeyDown(KeyBindings.talismanValueHud))
            mc.displayGuiScreen(null)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }
}
