package aurocosh.divinefavor.client.gui.blocks.medium;

import aurocosh.divinefavor.common.block.medium.ContainerMediumWithStone;
import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiIronMediumWithStone extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_IMMATERIAL_MEDIUM_WITH_STONE);

    TileMedium ironMedium;
    ItemStack stoneStack;

    public GuiIronMediumWithStone(EntityPlayer player, TileMedium medium) {
        super(new ContainerMediumWithStone(player, medium));
        this.ironMedium = medium;
        stoneStack = ironMedium.getStoneStack();
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
        mc.getRenderItem().renderItemIntoGUI(stoneStack, guiLeft + 80, guiTop + 36);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
