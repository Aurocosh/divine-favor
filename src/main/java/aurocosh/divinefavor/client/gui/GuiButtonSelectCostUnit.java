package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.LibGuiIDs;
import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.constants.LibResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSelectCostUnit extends GuiButton {
    private static final ResourceLocation textureInactive = new ResourceLocation(LibResources.GUI_SELECT_COST_UNIT_BUTTON);

    GuiTalismanContainer gui;

    public GuiButtonSelectCostUnit(GuiTalismanContainer gui, int id, int x, int y) {
        super(id, x, y, 10, 10, "");
        this.gui = gui;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(enabled) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int k = getHoverState(hovered);
            if(k == 2) {
                int test = 1;
            }
            mc.renderEngine.bindTexture(textureInactive);
            //GlStateManager.color(1F, 1F, 1F, 1F);
            drawTexturedModalRect(x, y, 0, k == 2 ? -10 : 0, width, height);

            //if(k == 2)
//                gui.tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("psimisc.learn"));
        }
    }
}