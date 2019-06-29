package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_drop.MemoryDropContainer
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import org.lwjgl.util.Rectangle

class GuiMemoryDrop(val player: EntityPlayer, internal var stack: ItemStack, hand: EnumHand)
    : GuiContainer(MemoryDropContainer()) {

    private var currentTemplateName = ""

    private lateinit var nameField: GuiTextField
    private lateinit var templateView: GuiBlockTemplateView

    override fun initGui() {
        super.initGui()
        xSize = WIDTH
        ySize = HEIGHT

        nameField = GuiTextField(0, fontRenderer, guiLeft + 8, guiTop + 6, 160, fontRenderer.FONT_HEIGHT)
        nameField.maxStringLength = 50
        nameField.visible = true

        val panel = Rectangle(8, 18, 160, 150)
        templateView = GuiBlockTemplateView(player.world, panel, mc, guiTop, guiLeft, 15)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(backgroundTexture)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)

        val templateName = stack.get(ItemMemoryDrop.templateName)
        setTemplateName(templateName)
        nameField.drawTextBox()

        val uuid = stack.get(ItemMemoryDrop.uuid)
        templateView.drawStructure(uuid)
    }

    private fun setTemplateName(name: String) {
        if (currentTemplateName == name)
            return
        nameField.text = name
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (nameField.textboxKeyTyped(typedChar, keyCode))
            stack.set(ItemMemoryDrop.templateName, nameField.text, true)
        else
            super.keyTyped(typedChar, keyCode)
    }


    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (nameField.mouseClicked(mouseX, mouseY, mouseButton)) {
            nameField.isFocused = true
        } else {
            nameField.isFocused = false
            templateView.mouseClicked(mouseX, mouseY, mouseButton)
            super.mouseClicked(mouseX, mouseY, mouseButton)
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        templateView.mouseReleased(mouseX, mouseY, state)
    }

    override fun drawGuiContainerForegroundLayer(j: Int, i: Int) {
        templateView.drawGuiContainerForegroundLayer(j, i)
        if (!nameField.isFocused && nameField.text.isEmpty())
            fontRenderer.drawString("template name", nameField.x - guiLeft + 4, nameField.y - guiTop, -10197916)
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        templateView.handleMouseInput()
    }

    companion object {
        const val WIDTH = 175
        const val HEIGHT = 178
        private val backgroundTexture = ResourceLocation(ConstResources.GUI_MEMORY_DROP)
    }
}
