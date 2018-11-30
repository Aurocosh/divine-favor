package aurocosh.divinefavor.client.gui.buttons;

import aurocosh.divinefavor.client.gui.IActionButton;
import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.item.talisman.capability.ITalismanCostHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncTalismanIndexes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import vazkii.arl.network.NetworkHandler;

public class GuiButtonSelectCostUnit extends GuiButton implements IActionButton {
    private static final ResourceLocation textureInactive = new ResourceLocation(LibResources.GUI_SELECT_COST_UNIT_BUTTON);

    private ITalismanCostHandler costHandler;
    private int unitIndex;
    private int costIndex;

    public GuiButtonSelectCostUnit(ITalismanCostHandler costHandler, int id, int x, int y, int unitIndex, int costIndex) {
        super(id, x, y, 10, 10, "");
        this.costHandler = costHandler;
        this.unitIndex = unitIndex;
        this.costIndex = costIndex;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(enabled) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int k = getHoverState(hovered);

            mc.renderEngine.bindTexture(textureInactive);
            float textureStartY = 0;
            if(costHandler.getSelectedUnitIndex() == unitIndex)
                textureStartY = 20;
            else if(k == 2)
                textureStartY = 10;

            //GlStateManager.color(1F, 1F, 1F, 1F);
            drawModalRectWithCustomSizedTexture (x, y, 0, textureStartY, width, height,10,30);

            //if(k == 2)
//                gui.tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("psimisc.learn"));
        }
    }

    @Override
    public void performAction() {
        costHandler.setUnitIndex(unitIndex);
        costHandler.setCostIndex(costIndex);
        MessageSyncTalismanIndexes message = new MessageSyncTalismanIndexes(unitIndex,costIndex);
        NetworkHandler.INSTANCE.sendToServer(message);
    }
}