package aurocosh.divinefavor.client.gui.buttons;

import aurocosh.divinefavor.client.gui.IActionButton;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSelectCostUnit extends GuiButton implements IActionButton {
    private static final ResourceLocation textureInactive = new ResourceLocation(ConstResources.GUI_SELECT_COST_UNIT_BUTTON);

    private int unitIndex;
    private int costIndex;

    public GuiButtonSelectCostUnit(int id, int x, int y, int unitIndex, int costIndex) {
        super(id, x, y, 10, 10, "");
        this.unitIndex = unitIndex;
        this.costIndex = costIndex;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (enabled) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int k = getHoverState(hovered);

            mc.renderEngine.bindTexture(textureInactive);
            float textureStartY = 0;
            if (0 == unitIndex)
                textureStartY = 20;
            else if (k == 2)
                textureStartY = 10;

            //GlStateManager.color(1F, 1F, 1F, 1F);
            drawModalRectWithCustomSizedTexture(x, y, 0, textureStartY, width, height, 10, 30);

            //if(k == 2)
//                gui.tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("psimisc.learn"));
        }
    }

    @Override
    public void performAction() {
    }
}