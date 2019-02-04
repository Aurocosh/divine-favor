package aurocosh.divinefavor.client.gui.blocks;

import aurocosh.divinefavor.common.block.bath_heater.ContainerBathHeater;
import aurocosh.divinefavor.common.block.bath_heater.TileBathHeater;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBathHeater extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_BATH_HEATER);

    TileBathHeater bathHeater;

    public GuiBathHeater(EntityPlayer player, TileBathHeater bathHeater) {
        super(new ContainerBathHeater(player, bathHeater));
        this.bathHeater = bathHeater;
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (bathHeater.isBurning()) {
            int k = bathHeater.getClientProgressBurning() * 13 / 100;
            this.drawTexturedModalRect(guiLeft + 81, guiTop + 42 + 12 - k, 176, 12 - k, 14, k + 1);
        }


        if (bathHeater.isBurning()) {
            int k = bathHeater.getClientProgressEffect() * 30 / 100;
            this.drawTexturedModalRect(guiLeft + 47 + 29 - k, guiTop + 25, 176 + 29 - k, 28, k + 1, 12);
            this.drawTexturedModalRect(guiLeft + 100, guiTop + 25, 176, 15, k + 1, 12);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
