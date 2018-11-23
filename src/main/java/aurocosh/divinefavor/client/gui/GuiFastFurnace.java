package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.block.tile.TileFastFurnace;
import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.block.tile.container.ContainerFastFurnace;
import aurocosh.divinefavor.common.block.tile.container.ContainerIronMedium;
import aurocosh.divinefavor.common.lib.LibResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFastFurnace extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_FAST_FURNACE);

    TileFastFurnace fastFurnace;

    public GuiFastFurnace(EntityPlayer player, TileFastFurnace fastFurnace) {
        super(new ContainerFastFurnace(player, fastFurnace));
        this.fastFurnace = fastFurnace;
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        renderHoveredToolTip(mouseX,mouseY);

        if (fastFurnace.getClientProgress() > 0) {
            int percentage = 100 - fastFurnace.getClientProgress() * 100 / TileFastFurnace.MAX_PROGRESS;
            drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX,mouseY);
    }
}
