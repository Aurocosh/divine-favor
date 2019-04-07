package aurocosh.divinefavor.common.potions.curses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.curse.evil_eye.EvilEyeData;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(Side.CLIENT)
public class PotionEvilEye extends ModPotion {
    private static ResourceLocation EVIL_EYE_BLUR_TEXTURE = new ResourceLocation(ConstResources.GUI_EVIL_EYE_BLUR);

    public PotionEvilEye() {
        super("evil_eye", true, 0x7FB8A4);
        setIsCurse(true);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            livingBase.removePotionEffect(ModCurses.evil_eye);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if(!player.isPotionActive(ModCurses.evil_eye))
            return;

        EvilEyeData evilEyeData = PlayerData.get(player).getEvilEyeData();
        int severity = evilEyeData.getSeverity();

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution resolution = event.getResolution();

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
//        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float colorAlpha = severity * ConfigSpells.evilEye.opacityPerSeverity;
        GlStateManager.color(1.0f, 1.0f, 1.0f, colorAlpha);
//        GlStateManager.disableAlpha();
        mc.getTextureManager().bindTexture(EVIL_EYE_BLUR_TEXTURE);
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
//        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
