package aurocosh.divinefavor.client.gui.blocks.soulbound_lectern

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternWithShard
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern
import aurocosh.divinefavor.common.constants.ConstResources
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

class GuiSoulboundLecternWithShard(player: EntityPlayer, lectern: TileSoulboundLectern) : GuiContainer(ContainerSoulboundLecternWithShard(player, lectern)) {

    private val shardStack: ItemStack = lectern.shardStack

    override fun initGui() {
        xSize = WIDTH
        ySize = HEIGHT
        super.initGui()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(textureBound)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
        mc.renderItem.renderItemIntoGUI(shardStack, guiLeft + 80, guiTop + 36)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    companion object {
        private val WIDTH = 175
        private val HEIGHT = 165
        private val textureBound = ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_WITH_SHARD)
    }
}
