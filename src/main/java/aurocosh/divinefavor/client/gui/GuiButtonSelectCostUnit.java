package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.item.talisman.capability.ITalismanCostHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncTalismanUnitIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import vazkii.arl.network.NetworkHandler;

public class GuiButtonSelectCostUnit extends GuiButton implements IActionButton {
    private static final ResourceLocation textureInactive = new ResourceLocation(LibResources.GUI_SELECT_COST_UNIT_BUTTON);

    private ITalismanCostHandler costHandler;
    private int slotIndex;

    public GuiButtonSelectCostUnit(ITalismanCostHandler costHandler, int id, int x, int y, int slotIndex) {
        super(id, x, y, 10, 10, "");
        this.costHandler = costHandler;
        this.slotIndex = slotIndex;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(enabled) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int k = getHoverState(hovered);

            mc.renderEngine.bindTexture(textureInactive);
            int textureStartY = 0;
            if(costHandler.getSelectedUnitIndex() == slotIndex)
                textureStartY = -20;
            else if(k == 2)
                textureStartY = -10;

            //GlStateManager.color(1F, 1F, 1F, 1F);
            drawTexturedModalRect(x, y, 0, textureStartY, width, height);

            //if(k == 2)
//                gui.tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("psimisc.learn"));
        }
    }

    @Override
    public void performAction() {
        costHandler.setSelectedUnitIndex(slotIndex);
        MessageSyncTalismanUnitIndex message = new MessageSyncTalismanUnitIndex(slotIndex);
        NetworkHandler.INSTANCE.sendToServer(message);
    }
}