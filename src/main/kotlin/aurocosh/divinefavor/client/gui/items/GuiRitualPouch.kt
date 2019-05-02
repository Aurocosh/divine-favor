package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class GuiRitualPouch(player: EntityPlayer, pouch: ItemStack) : GuiContainer(RitualBagContainer(player, pouch.getCapability<IItemHandler>(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!)) {

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
        private val texture = ResourceLocation(ConstResources.GUI_RITUAL_POUCH)
    }
}
