package aurocosh.divinefavor.client.gui.blocks.soulbound_lectern

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternEmpty
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

class GuiSoulboundLecternEmpty(player: EntityPlayer, lectern: TileSoulboundLectern) : GuiContainer(ContainerSoulboundLecternEmpty(player, lectern)) {

    override fun initGui() {
        xSize = WIDTH
        ySize = HEIGHT
        super.initGui()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(textureUnbound)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    companion object {
        private val WIDTH = 175
        private val HEIGHT = 165
        private val textureUnbound = ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_EMPTY)
    }
}
