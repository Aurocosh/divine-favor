package aurocosh.divinefavor.client.gui.blocks.soulbound_lectern;

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternUnbound;
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSoulboundLecternEmpty extends GuiContainer {
    private static final int WIDTH = 175;
    private static final int HEIGHT = 165;
    private static final ResourceLocation textureUnbound = new ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_UNBOUND);

    public GuiSoulboundLecternEmpty(EntityPlayer player, TileSoulboundLectern lectern) {
        super(new ContainerSoulboundLecternUnbound(player, lectern));
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(textureUnbound);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX,mouseY);
    }
}
