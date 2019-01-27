package aurocosh.divinefavor.client.core.handler.hud;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

public class UtilHUD {
    private static final int REMAINING_HIGHLIGHT_TICKS_INDEX = 12;

    public static void drawTalismanDescription(Minecraft mc, ScaledResolution res, float partialTicks, EntityPlayer player, ItemStack talismanStack) {
        ItemTalisman talisman = (ItemTalisman) talismanStack.getItem();
        String description = talisman.getUseInfo(player);

        ModFavor favor = talisman.getFavor();
        ResourceLocation icon = favor.getIcon();

        FavorData favorData = PlayerData.get(player).getFavorData();
        int value = favorData.get(favor).getValue();
        int maxLimit = favorData.get(favor).getMaxLimit();
        String favorDescription = value + "/" + maxLimit;

        int alpha = 255;
        int color = (0 << 0) + (128 << 8) + (0 << 16) + (alpha << 24);

        int x = res.getScaledWidth() / 2;
        int y = res.getScaledHeight() - 71;

        int ticks = ReflectionHelper.getPrivateValue(GuiIngame.class, mc.ingameGUI, REMAINING_HIGHLIGHT_TICKS_INDEX);
//        ticks -= 10;
        if (ticks <= 0)
            y += 12;

        if (mc.player.capabilities.isCreativeMode)
            y += 14;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        int wd = mc.fontRenderer.getStringWidth(description) / 2;
        mc.fontRenderer.drawStringWithShadow(description, x + 24, y, color);

        int wfd = mc.fontRenderer.getStringWidth(favorDescription);
        mc.fontRenderer.drawStringWithShadow(favorDescription, x - wfd - 22, y, color);

        // talisman icon
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 5, y - 6, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);
        mc.getRenderItem().renderItemIntoGUI(talismanStack, 0, 0);
        GlStateManager.popMatrix();

        // favor icon
        mc.renderEngine.bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x - 21, y - 6, 0, 0, 16, 16, 16, 16);

        GlStateManager.disableBlend();
    }
}
