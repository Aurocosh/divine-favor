package aurocosh.divinefavor.common.potions.curses

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.potions.base.potion.ModPotion
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
class PotionEvilEye : ModPotion("evil_eye", 0x7FB8A4) {
    init {
        setIsCurse(true)
    }

    override fun onPotionAdded(livingBase: EntityLivingBase) {
        super.onPotionAdded(livingBase)
        if (livingBase !is EntityPlayer)
            livingBase.removePotionEffect(ModCurses.evil_eye)
    }

    override fun isReady(duration: Int, amplifier: Int): Boolean {
        return true
    }

    companion object {
        private val EVIL_EYE_BLUR_TEXTURE = ResourceLocation(ConstResources.GUI_EVIL_EYE_BLUR)

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        fun onDraw(event: RenderGameOverlayEvent.Post) {
            if (event.type != RenderGameOverlayEvent.ElementType.ALL)
                return

            val player = DivineFavor.proxy.clientPlayer
            if (!player.isPotionActive(ModCurses.evil_eye))
                return

            val severity = player.divinePlayerData.evilEyeData.severity

            val mc = Minecraft.getMinecraft()
            val resolution = event.resolution

            GlStateManager.disableDepth()
            GlStateManager.enableBlend()
            GlStateManager.depthMask(false)
            //        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            val colorAlpha = severity * ConfigSpell.evilEye.opacityPerSeverity
            GlStateManager.color(1.0f, 1.0f, 1.0f, colorAlpha)
            //        GlStateManager.disableAlpha();
            mc.textureManager.bindTexture(EVIL_EYE_BLUR_TEXTURE)
            val tessellator = Tessellator.getInstance()

            val height = resolution.scaledHeight.toDouble()
            val width = resolution.scaledWidth.toDouble()
            val bufferBuilder = tessellator.buffer
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
            bufferBuilder.pos(0.0, height, -90.0).tex(0.0, 1.0).endVertex()
            bufferBuilder.pos(width, height, -90.0).tex(1.0, 1.0).endVertex()
            bufferBuilder.pos(width, 0.0, -90.0).tex(1.0, 0.0).endVertex()
            bufferBuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex()

            tessellator.draw()
            GlStateManager.depthMask(true)
            GlStateManager.enableDepth()
            //        GlStateManager.enableAlpha();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        }
    }
}
