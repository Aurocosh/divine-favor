package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.SpellBowContainer
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation

class GuiSpellBow(player: EntityPlayer, spellBow: ItemStack, hand: EnumHand) : GuiContainer(SpellBowContainer(player, SpellBowDataHandler.get(spellBow)!!, hand)) {

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
        private val texture = ResourceLocation(ConstResources.GUI_GRIMOIRE)
    }
}
