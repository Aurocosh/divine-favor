package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_pouch.MemoryPouchTemplateContainer
import aurocosh.divinefavor.common.item.memory_pouch.capability.IMemoryPouch
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncMemoryPouchDropName
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import org.lwjgl.util.Rectangle
import java.util.*

class GuiMemoryPouchTemplate(val player: EntityPlayer, internal var stack: ItemStack, hand: EnumHand, private val pouch: IMemoryPouch = stack.cap(CAPABILITY_MEMORY_POUCH))
    : GuiContainer(MemoryPouchTemplateContainer(player, pouch, hand)) {

    private var currentTemplateName = ""
    private val playerSlotIndex = UtilPlayer.getHandIndex(player, hand)

    private lateinit var nameField: GuiTextField
    private lateinit var templateView: GuiBlockTemplateView

    override fun initGui() {
        super.initGui()
        xSize = WIDTH
        ySize = HEIGHT

        nameField = GuiTextField(0, fontRenderer, guiLeft + 8, guiTop + 6, 160, fontRenderer.FONT_HEIGHT)
        nameField.maxStringLength = 50
        nameField.visible = true

        val panel = Rectangle(8, 18, 160, 95)
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

        val selectedStack = getSelectedStack()
        setTemplateName(selectedStack.get(ItemMemoryDrop.templateName))

        nameField.drawTextBox()

        val uuid = getSelectedTemplate()
        templateView.drawStructure(uuid)

        val slotIndex = pouch.selectedSlotIndex
        val slot = inventorySlots.getSlot(slotIndex)

        mc.textureManager.bindTexture(dropSelectorTexture)
        Gui.drawModalRectWithCustomSizedTexture(slot.xPos + guiLeft, slot.yPos + guiTop, 0f, 0f, 16, 16, 16f, 16f)
    }

    private fun setTemplateName(name: String) {
        if (currentTemplateName == name)
            return
        nameField.text = name
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (nameField.textboxKeyTyped(typedChar, keyCode)) {
            val selectedStack = pouch.getSelectedStack()
            if (!selectedStack.isEmpty) {
                val text = nameField.text
                selectedStack.set(ItemMemoryDrop.templateName, text)
                MessageSyncMemoryPouchDropName(playerSlotIndex, pouch.selectedSlotIndex, text).send()
            }
        } else
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

    private fun getSelectedTemplate(): UUID {
        val stack = getSelectedStack()
        return stack.get(ItemMemoryDrop.uuid)
    }

    private fun getSelectedStack(): ItemStack {
        val slot = slotUnderMouse
        return if (slot != null && slot.hasStack) slot.stack else pouch.getSelectedStack()
    }

    companion object {
        const val WIDTH = 175
        const val HEIGHT = 178
        private val backgroundTexture = ResourceLocation(ConstResources.GUI_MEMORY_POUCH_TEMPLATE)
        private val dropSelectorTexture = ResourceLocation(ConstResources.GUI_MEMORY_POUCH_DROP_SELECTOR)
    }
}
