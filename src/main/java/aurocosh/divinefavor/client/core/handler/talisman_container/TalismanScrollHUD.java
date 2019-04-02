package aurocosh.divinefavor.client.core.handler.talisman_container;

import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TalismanScrollHUD {
    private int state = 0;
    private ITalismanContainer handler = null;

    private ItemStack selectedStack = ItemStack.EMPTY;
    private List<ItemStack> nextStacks = null;
    private List<ItemStack> previousStacks = null;

    @SideOnly(Side.CLIENT)
    public void draw(Minecraft mc, int width, int height, ITalismanContainer talismanContainer) {
        refreshItemStacks(talismanContainer);
        if (selectedStack.isEmpty())
            return;

        int alpha = 255;
        int x = width / 2;
        int y = 10;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);
        mc.getRenderItem().renderItemIntoGUI(selectedStack, 0, 0);
        for (int i = 0; i < nextStacks.size(); i++)
            mc.getRenderItem().renderItemIntoGUI(nextStacks.get(i), i * 18 + 21, -6);
        for (int i = 0; i < previousStacks.size(); i++)
            mc.getRenderItem().renderItemIntoGUI(previousStacks.get(i), -i * 18 - 21, -6);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

    private void refreshItemStacks(ITalismanContainer talismanContainer) {
        if (handler == talismanContainer && state == talismanContainer.getState())
            return;

        handler = talismanContainer;
        state = talismanContainer.getState();

        selectedStack = talismanContainer.getSelectedStack();
        previousStacks = new ArrayList<>(talismanContainer.getPreviousStacks());
        nextStacks = talismanContainer.getNextStacks();

        for (ItemStack stack : nextStacks)
            previousStacks.remove(stack);
    }
}
