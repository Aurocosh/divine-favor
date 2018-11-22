package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.block.tile.TileIronMedium;
import aurocosh.divinefavor.common.block.tile.container.ContainerIronMedium;
import aurocosh.divinefavor.common.lib.LibResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiIronMedium extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_IRON_MEDIUM);

    TileIronMedium ironMedium;

    public GuiIronMedium(EntityPlayer player, TileIronMedium ironMedium) {
        super(new ContainerIronMedium(player, ironMedium));
        this.ironMedium = ironMedium;
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
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX,mouseY);
    }
}
