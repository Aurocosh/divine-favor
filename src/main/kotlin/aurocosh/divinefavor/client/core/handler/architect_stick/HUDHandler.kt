package aurocosh.divinefavor.client.core.handler.architect_stick

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.tools.mystic_architect_stick.ItemMysticArchitectStick
import aurocosh.divinefavor.common.lib.extensions.hasKey
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.util.UtilGui
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID, value = [Side.CLIENT])
object HUDHandler {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    fun renderWorldLastEvent(event: RenderWorldLastEvent) {
        val player = DivineFavor.proxy.clientPlayer
        val stack = player.heldItemMainhand
        if (stack.item !is ItemMysticArchitectStick)
            return

        if (!stack.hasKey(ItemMysticArchitectStick.TAG_POS_FIRST, ItemMysticArchitectStick.TAG_POS_SECOND))
            return

        val compound = stack.compound
        val startPos = compound.getBlockPos(ItemMysticArchitectStick.TAG_POS_FIRST)
        val endPos = compound.getBlockPos(ItemMysticArchitectStick.TAG_POS_SECOND)

        val mc = Minecraft.getMinecraft()
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

        //Calculate the players current position, which is needed later
        val partialTicks = event.partialTicks
        val currentPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks
        val currentPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks
        val currentPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks

        //We want to draw from the starting position to the (ending position)+1
        val x = if (startPos.x <= endPos.x) startPos.x else endPos.x
        val y = if (startPos.y <= endPos.y) startPos.y else endPos.y
        val z = if (startPos.z <= endPos.z) startPos.z else endPos.z
        val dx = if (startPos.x > endPos.x) startPos.x + 1 else endPos.x + 1
        val dy = if (startPos.y > endPos.y) startPos.y + 1 else endPos.y + 1
        val dz = if (startPos.z > endPos.z) startPos.z + 1 else endPos.z + 1

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

        GlStateManager.pushMatrix()
        GlStateManager.translate(-currentPosX, -currentPosY, -currentPosZ)//The render starts at the player, so we subtract the player coords and move the render to 0,0,0

        GlStateManager.disableLighting()
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)

        UtilGui.renderBox(tessellator, bufferBuilder, BlockPos(x, y, z), BlockPos(dx, dy, dz), 255, 223, 127)

        GlStateManager.glLineWidth(1.0f)
        GlStateManager.enableLighting()
        GlStateManager.enableTexture2D()
        GlStateManager.enableDepth()
        GlStateManager.depthMask(true)

        GlStateManager.popMatrix()
    }
}