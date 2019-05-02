package aurocosh.divinefavor.client.gui.blocks

import aurocosh.divinefavor.common.block.bath_heater.ContainerBathHeater
import aurocosh.divinefavor.common.block.bath_heater.TileBathHeater
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

class GuiBathHeater(player: EntityPlayer, private var bathHeater: TileBathHeater) : GuiContainer(ContainerBathHeater(player, bathHeater)) {

    override fun initGui() {
        xSize = WIDTH
        ySize = HEIGHT
        super.initGui()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(texture)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)

        if (bathHeater.isBurning) {
            val k = bathHeater.clientProgressBurning * 13 / 100
            this.drawTexturedModalRect(guiLeft + 81, guiTop + 42 + 12 - k, 176, 12 - k, 14, k + 1)
        }


        if (bathHeater.isBurning) {
            val k = bathHeater.clientProgressEffect * 30 / 100
            this.drawTexturedModalRect(guiLeft + 47 + 29 - k, guiTop + 25, 176 + 29 - k, 28, k + 1, 12)
            this.drawTexturedModalRect(guiLeft + 100, guiTop + 25, 176, 15, k + 1, 12)
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    companion object {
        val WIDTH = 175
        val HEIGHT = 165
        private val texture = ResourceLocation(ConstResources.GUI_BATH_HEATER)
    }
}
