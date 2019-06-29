package aurocosh.divinefavor.client.gui.talisman

import aurocosh.divinefavor.client.core.handler.KeyBindings
import aurocosh.divinefavor.client.gui.elements.GuiButtonStack
import aurocosh.divinefavor.client.gui.interfaces.IButtonContainer
import aurocosh.divinefavor.client.gui.interfaces.IResettable
import aurocosh.divinefavor.client.gui.interfaces.IScrollable
import aurocosh.divinefavor.client.gui.interfaces.ITooltipProvider
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.talisman.ITalismanStackContainer
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import aurocosh.divinefavor.common.lib.TooltipCache
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTalismanPropertyIndex
import aurocosh.divinefavor.common.stack_properties.properties.StackProperty
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyEnum
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
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
    private val properties: List<StackProperty<out Any>>
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

        val container = stack.item as ITalismanStackContainer
        this.itemStack = container.getTalismanStack(stack)
        item = this.itemStack.item as ItemTalisman

        selectedPropertyIndex = item.properties.getSelectedIndex(stack)
        properties = item.properties.list
    }

    override fun initGui() {
        properties.forEach(this::addPropertyToGui)
        val buttonStack = GuiButtonStack(width / 2 - 8, 20, 16, 16, itemStack, Color.LIGHT_GRAY) {}
        buttonStack.components.forEach { this.addButton(it) }
    }

    private fun addPropertyToGui(property: StackProperty<out Any>) {
        if (!property.showInGui)
            return

        val index = propertyGuiElements.size
        val selector = {
            selectedPropertyIndex = index
            item.properties.setSelectedIndex(itemStack, index)
            MessageSyncTalismanPropertyIndex(playerSlot, index).send()
        }

        when (property) {
            is StackPropertyInt -> {
                val slider = PropertyGuiHelper.addNewSlider(property, itemStack, nextElementX, nextElementY, elementWidth, elementHeight, selector)
                addGuiElement(slider)
            }
            is StackPropertyEnum -> {
                val slider = PropertyGuiHelper.addNewEnumSlider(property, itemStack, nextElementX, nextElementY, elementWidth, elementHeight, selector)
                addGuiElement(slider)
            }
            is StackPropertyBool -> {
                val slider = PropertyGuiHelper.getToggle(property, itemStack, nextElementX, nextElementY, elementWidth, elementHeight, selector)
                addGuiElement(slider)
            }
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
        if (selectedPropertyIndex < 0 || selectedPropertyIndex >= propertyGuiElements.size)
            return

        val container = propertyGuiElements[selectedPropertyIndex]
        val rect = container.rect

        mc.textureManager.bindTexture(marker)
        Gui.drawModalRectWithCustomSizedTexture(rect.x + rect.width + markerMargin, rect.y, 0f, 0f, 16, 16, 16f, 16f)
    }

    private fun renderTooltips() {
        val xTool = width - tooltipWidth - xMargin
        val yTool = yMargin

        val tooltipProviders = buttonList.S.filterIsInstance<ITooltipProvider>().filter(ITooltipProvider::isSelected)

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
        if (mouseButton == 2) {
            getSelectedElement<IResettable>()?.reset()
        } else if (mouseButton == 1) {
            if (propertyGuiElements.isEmpty())
                return
            selectedPropertyIndex = propertyGuiElements.S.mapIndexed { i, element -> Pair(i, element) }.filter { it.second.isHovered() }.map { it.first }.firstOrNull()
                    ?: 0
        } else
            super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val scrollable = getSelectedElement<IScrollable>()
        scrollable?.scroll(UtilMouse.getScrollCount(scrollable.fastScrollValue))
    }

    private inline fun <reified T> getSelectedElement(): T? {
        val hoveredElement = propertyGuiElements.S.filter(IButtonContainer::isHovered).filterIsInstance<T>().firstOrNull()
        if (hoveredElement != null)
            return hoveredElement
        if (0 <= selectedPropertyIndex && selectedPropertyIndex < propertyGuiElements.size) {
            val container = propertyGuiElements[selectedPropertyIndex]
            if (container is T)
                return container
        }
        return null
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
