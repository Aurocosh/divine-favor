package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.memory_pouch.MemoryPouchNormalContainer
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.lib.extensions.cap
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation

class GuiMemoryPouchNormal(player: EntityPlayer, internal var stack: ItemStack, hand: EnumHand)
    : GuiContainer(MemoryPouchNormalContainer(player, stack.cap(CAPABILITY_MEMORY_POUCH), hand)) {

    override fun initGui() {
        xSize = WIDTH
        ySize = HEIGHT
        super.initGui()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(texture)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    companion object {
        val WIDTH = 175
        val HEIGHT = 165
        private val texture = ResourceLocation(ConstResources.GUI_MEMORY_POUCH)
    }
}
