package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.gui.elements.GuiButtonStack
import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.IScrollable
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.item.talisman.properties.TalismanProperty
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyBool
import aurocosh.divinefavor.common.item.talisman.properties.TalismanPropertyInt
import aurocosh.divinefavor.common.item.talisman_tools.TalismanContainerAdapter
import aurocosh.divinefavor.common.lib.TooltipCache
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyIndex
import aurocosh.divinefavor.common.util.UtilMouse
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.settings.GameSettings
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

class GuiTalismanProperties(stack: ItemStack, private val playerSlot: Int) : GuiScreen() {
    private val item: ItemTalisman
    private val itemStack: ItemStack

    private var selectedPropertyIndex: Int
    private val properties: List<TalismanProperty<out Any>>
    private var propertyGuiElements: MutableList<IButtonContainer> = ArrayList()

    private val yMargin = 20
    private val xMargin = 20
    private val markerMargin = 0

    private val tooltipWidth = 140
    private val tooltipHeight = 500

    private val elementWidth = 140
    private val elementHeight = 14

    private var nextElementX = xMargin
    private var nextElementY = yMargin

    private var elementStepY = 20
    private val tooltipStepY = 10

    private val tooltipCache = TooltipCache()

    init {
        mc = Minecraft.getMinecraft()

        this.itemStack = TalismanContainerAdapter.getTalismanStack(stack)
        item = this.itemStack.item as ItemTalisman

        selectedPropertyIndex = item.getSelectedPropertyIndex(stack)
        properties = item.properties
    }

    override fun initGui() {
        properties.forEach(this::addPropertyToGui)
        val buttonStack = GuiButtonStack(width / 2 - 8, 20, 16, 16, itemStack, Color.LIGHT_GRAY) {}
        buttonStack.components.forEach { this.addButton(it) }
    }

    private fun addPropertyToGui(property: TalismanProperty<out Any>) {
        val index = propertyGuiElements.size
        val selector = {
            selectedPropertyIndex = index
            item.setSelectedPropertyIndex(itemStack, index)
            MessageSyncTalismanPropertyIndex(playerSlot, index).send()
        }

        when (property) {
            is TalismanPropertyInt -> {
                val slider = PropertyGuiHelper.addNewSlider(property, itemStack, playerSlot, nextElementX, nextElementY, elementWidth, elementHeight, selector)
                addGuiElement(slider)
            }
            is TalismanPropertyBool -> {
                val slider = PropertyGuiHelper.getToggle(property, itemStack, playerSlot, nextElementX, nextElementY, elementWidth, elementHeight, selector)
                addGuiElement(slider)
            }
            else -> DivineFavor.logger.error("Attempted to add unknown type of property")
        }
    }

    private fun addGuiElement(element: IButtonContainer) {
        element.components.forEach { this.addButton(it) }
        propertyGuiElements.add(element)
        nextElementY += elementStepY
    }

    override fun drawScreen(mx: Int, my: Int, partialTicks: Float) {
        super.drawScreen(mx, my, partialTicks)
        drawMarker()
        renderTooltips();
    }

    fun drawMarker() {
        if (selectedPropertyIndex == -1)
            return

        val container = propertyGuiElements[selectedPropertyIndex]
        val rect = container.rect

        mc.textureManager.bindTexture(marker)
        Gui.drawModalRectWithCustomSizedTexture(rect.x + rect.width + markerMargin, rect.y, 0f, 0f, 16, 16, 16f, 16f)
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

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val scrollable = propertyGuiElements.S.filter(IButtonContainer::isHovered).filterIsInstance<IScrollable>().firstOrNull()
        if (scrollable != null) {
            scrollable.scroll(UtilMouse.getScrollCount(scrollable.fastScrollValue))
        } else if (selectedPropertyIndex != -1) {
            val container = propertyGuiElements[selectedPropertyIndex]
            if (container is IScrollable)
                container.scroll(UtilMouse.getScrollCount(container.fastScrollValue))
        }
    }

    override fun updateScreen() {
        if (!GameSettings.isKeyDown(KeyBindings.talismanValueHud))
            mc.displayGuiScreen(null)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    companion object {
        private val marker = ResourceLocation(ConstResources.GUI_TALISMAN_MARKER)
    }
}
