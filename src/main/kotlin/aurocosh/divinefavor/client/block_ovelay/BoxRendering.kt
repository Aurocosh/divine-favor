package aurocosh.divinefavor.client.block_ovelay

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object BoxRendering {
    private val mc = Minecraft.getMinecraft()

    @SideOnly(Side.CLIENT)
    fun render(lastEvent: RenderWorldLastEvent, player: EntityPlayer, startPos: BlockPos, endPos: BlockPos) {

        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

        //Calculate the players current position, which is needed later
        val partialTicks = lastEvent.partialTicks
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

        renderBox(tessellator, bufferBuilder, BlockPos(x, y, z), BlockPos(dx, dy, dz), 255, 223, 127)

        GlStateManager.glLineWidth(1.0f)
        GlStateManager.enableLighting()
        GlStateManager.enableTexture2D()
        GlStateManager.enableDepth()
        GlStateManager.depthMask(true)

        GlStateManager.popMatrix()
    }

    @SideOnly(Side.CLIENT)
    fun renderBox(tessellator: Tessellator, bufferBuilder: BufferBuilder, start: BlockPos, end: BlockPos, red: Int, green: Int, blue: Int) {
        GlStateManager.glLineWidth(2.0f)
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR)
        bufferBuilder.pos(start.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(green.toFloat(), green.toFloat(), green.toFloat(), 0.0f).endVertex()
        bufferBuilder.pos(start.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(green, blue, blue, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), start.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), start.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(blue, blue, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), end.y.toDouble(), start.z.toDouble()).color(blue, green, blue, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), end.y.toDouble(), start.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), end.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), end.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), end.y.toDouble(), start.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), end.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(start.x.toDouble(), start.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), start.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), end.y.toDouble(), end.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), end.y.toDouble(), start.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(green, green, green, red).endVertex()
        bufferBuilder.pos(end.x.toDouble(), start.y.toDouble(), start.z.toDouble()).color(green.toFloat(), green.toFloat(), green.toFloat(), 0.0f).endVertex()
        tessellator.draw()
        GlStateManager.glLineWidth(1.0f)
    }
}
