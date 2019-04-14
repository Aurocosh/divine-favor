package aurocosh.divinefavor.common.potions.potions;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber
public class PotionPrismaticEyes extends ModPotionToggle {
    private static ResourceLocation EVIL_PRISMATIC_EYES_TEXTURE = new ResourceLocation(ConstResources.GUI_PRISMATIC_EYES_BLUR);

    public PotionPrismaticEyes() {
        super("prismatic_eyes", true, 0x7FB8A4);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.player.isPotionActive(ModPotions.prismatic_eyes))
            return;

        ScaledResolution resolution = event.getResolution();

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.2f);
        mc.getTextureManager().bindTexture(EVIL_PRISMATIC_EYES_TEXTURE);
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
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
