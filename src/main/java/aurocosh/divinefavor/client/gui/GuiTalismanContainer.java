package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import aurocosh.divinefavor.common.item.talisman.capability.ITalismanCostHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiTalismanContainer extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_TALISMAN);
    private ItemStack talisman;

    public GuiTalismanContainer(EntityPlayer player, ItemStack talisman) {
        super(new ContainerTalisman(player, talisman));
        this.talisman = talisman;
    }

    @Override
    public void initGui() {
        this.xSize = 176;
        this.ySize = 166;
        super.initGui();

        ITalismanCostHandler costHandler = TalismanDataHandler.getHandler(talisman);
        if(costHandler == null)
            return;

        int nextButtonId = 0;

        int xShift = 14;
        for (int i = 0; i < 5; i++) {
            int id = nextButtonId++;
            GuiButtonSelectCostUnit button = new GuiButtonSelectCostUnit(costHandler, id, guiLeft + 7, guiTop + 81 + xShift * i, i);
            this.buttonList.add(button);
        }
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
        IActionButton button = (IActionButton)B;
        if(button == null)
            return;
        button.performAction();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}