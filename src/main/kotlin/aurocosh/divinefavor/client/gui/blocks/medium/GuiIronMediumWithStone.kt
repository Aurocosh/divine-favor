package aurocosh.divinefavor.client.gui.blocks.medium

import aurocosh.divinefavor.common.block.medium.ContainerMediumWithStone
import aurocosh.divinefavor.common.block.medium.TileMedium
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

class GuiIronMediumWithStone(player: EntityPlayer, ironMedium: TileMedium) : GuiContainer(ContainerMediumWithStone(player, ironMedium)) {
    var stoneStack: ItemStack = ironMedium.stoneStack

    override fun initGui() {
        xSize = WIDTH
        ySize = HEIGHT
        super.initGui()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(texture)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
        mc.renderItem.renderItemIntoGUI(stoneStack, guiLeft + 80, guiTop + 36)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    companion object {
        val WIDTH = 175
        val HEIGHT = 165
        private val texture = ResourceLocation(ConstResources.GUI_IMMATERIAL_MEDIUM_WITH_STONE)
    }
}
