package aurocosh.divinefavor.client.gui.buttons;

import aurocosh.divinefavor.client.gui.IActionButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonSelectCost extends GuiButton implements IActionButton {
    private int unitIndex;
    private int costIndex;
    private ResourceLocation texture;

    public GuiButtonSelectCost(int id, int x, int y, int unitIndex, int costIndex, ResourceLocation texture) {
        super(id, x, y, 8, 8, "");
        this.unitIndex = unitIndex;
        this.costIndex = costIndex;
        this.texture = texture;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(enabled) {
            mc.renderEngine.bindTexture(texture);
            drawTexturedModalRect(x, y, 0, 0, width, height);

            //if(k == 2)
//                gui.tooltip.addValue(TextFormatting.GREEN + I18n.translateToLocal("psimisc.learn"));
        }
    }

    @Override
    public void performAction() {
    }
}