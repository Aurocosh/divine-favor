package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiTalismanContainer extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_TALISMAN);

    public GuiTalismanContainer(EntityPlayer player, ItemStack journal) {
        super(new ContainerTalisman(player, journal));
    }

    @Override
    public void initGui() {
        this.xSize = 176;
        this.ySize = 166;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}