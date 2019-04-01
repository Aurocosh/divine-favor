package aurocosh.divinefavor.client.core.handler.grimoire;

import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GrimoireScrollHUD {
    private int state = 0;
    private IGrimoireHandler handler = null;

    private ItemStack selectedStack = ItemStack.EMPTY;
    private List<ItemStack> nextStacks = null;
    private List<ItemStack> previousStacks = null;

    @SideOnly(Side.CLIENT)
    public void draw(Minecraft mc, ScaledResolution res, IGrimoireHandler grimoireHandler) {
        if (!KeyBindings.talismanScroll.isKeyDown())
            return;

        refreshItemStacks(grimoireHandler);
        if (selectedStack.isEmpty())
            return;

        int alpha = 255;
        int x = res.getScaledWidth() / 2;
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

    private void refreshItemStacks(IGrimoireHandler grimoireHandler) {
        if (handler != grimoireHandler || state != grimoireHandler.getState()) {
            handler = grimoireHandler;
            state = grimoireHandler.getState();

            selectedStack = grimoireHandler.getSelectedStack();
            previousStacks = new ArrayList<>(grimoireHandler.getPreviousStacks());
            nextStacks = grimoireHandler.getNextStacks();

            for (ItemStack stack : nextStacks)
                previousStacks.remove(stack);
        }
    }
}
