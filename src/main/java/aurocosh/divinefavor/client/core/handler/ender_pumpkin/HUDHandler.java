package aurocosh.divinefavor.client.core.handler.ender_pumpkin;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.ItemBlockEnderPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class HUDHandler {
    private static ResourceLocation ENDER_PUMPKIN_BLUR_TEX = new ResourceLocation(ConstResources.GUI_ENDER_PUMPKIN);

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ItemStack stack = player.inventory.armorInventory.get(3);
        if (!(stack.getItem() instanceof ItemBlockEnderPumpkin))
            return;

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution resolution = event.getResolution();

        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableAlpha();
        mc.getTextureManager().bindTexture(ENDER_PUMPKIN_BLUR_TEX);
        Tessellator tessellator = Tessellator.getInstance();

        double height = resolution.getScaledHeight();
        double width = resolution.getScaledWidth();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0d, height, -90.0d).tex(0.0d, 1.0d).endVertex();
        bufferbuilder.pos(width, height, -90.0d).tex(1.0d, 1.0d).endVertex();
        bufferbuilder.pos(width, 0.0d, -90.0d).tex(1.0d, 0.0d).endVertex();
        bufferbuilder.pos(0.0d, 0.0d, -90.0d).tex(0.0d, 0.0d).endVertex();

        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
