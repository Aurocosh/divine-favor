package aurocosh.divinefavor.client.core.handler.talisman;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

public class TalismanHUD {
    private static final int REMAINING_HIGHLIGHT_TICKS_INDEX = 12;

    public static void drawTalismanDescription(Minecraft mc, int width, int height, EntityPlayer player, ItemStack talismanStack, boolean drawName) {
        if (talismanStack.isEmpty())
            return;

        ItemTalisman talisman = (ItemTalisman) talismanStack.getItem();
        String description = talisman.getUseInfo(player);

        ModFavor favor = talisman.getFavor();
        ResourceLocation icon = favor.getIcon();

        FavorData favorData = PlayerData.get(player).getFavorData();
        int value = favorData.getFavor(favor.getId());
        int maxLimit = favorData.getMaxFavor(favor.getId());
        String favorDescription = value + "/" + maxLimit;

        int alpha = 255;
        int color = (0 << 0) + (128 << 8) + (0 << 16) + (alpha << 24);

        int x = width / 2;
        int y = height - 71;

        int ticks = ReflectionHelper.getPrivateValue(GuiIngame.class, mc.ingameGUI, REMAINING_HIGHLIGHT_TICKS_INDEX);
//        ticks -= 10;
        if (ticks <= 0)
            y += 12;

        if (mc.player.capabilities.isCreativeMode)
            y += 14;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);

        if (drawName)
            mc.fontRenderer.drawStringWithShadow(talisman.getTranslatedName(), 24, -10, color);

        mc.fontRenderer.drawStringWithShadow(description, 24, 0, color);

        int favorDescriptionWidth = mc.fontRenderer.getStringWidth(favorDescription);
        mc.fontRenderer.drawStringWithShadow(favorDescription, -favorDescriptionWidth - 22, 0, color);

        // talisman icon
        GlStateManager.scale(alpha / 255f, 1f, 1f);
        GlStateManager.color(1f, 1f, 1f);
        mc.getRenderItem().renderItemIntoGUI(talismanStack, 5, -6);

        // favor icon
        mc.renderEngine.bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(-21, -6, 0, 0, 16, 16, 16, 16);

        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }
}
