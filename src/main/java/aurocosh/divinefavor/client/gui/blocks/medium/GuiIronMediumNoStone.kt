package aurocosh.divinefavor.client.gui.blocks.medium

import aurocosh.divinefavor.common.block.medium.ContainerMediumNoStone
import aurocosh.divinefavor.common.block.medium.TileMedium
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation

class GuiIronMediumNoStone(player: EntityPlayer, ironMedium: TileMedium) : GuiContainer(ContainerMediumNoStone(player, ironMedium)) {

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
        private val texture = ResourceLocation(ConstResources.GUI_IMMATERIAL_MEDIUM_NO_STONE)
    }
}
