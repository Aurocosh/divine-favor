package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
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
        this.buttonList.add(new GuiButtonSelectCostUnit(this,1, guiLeft + 7, guiTop + 81));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton B)
    {
        if(B.id == 1)
        {
            System.out.println("My Button is Clicked!");
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}