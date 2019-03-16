package aurocosh.divinefavor.client.gui.blocks.medium;

import aurocosh.divinefavor.common.block.medium.ContainerMediumNoStone;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiIronMediumNoStone extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_IMMATERIAL_MEDIUM_NO_STONE);

    TileMedium ironMedium;

    public GuiIronMediumNoStone(EntityPlayer player, TileMedium ironMedium) {
        super(new ContainerMediumNoStone(player, ironMedium));
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
