package aurocosh.divinefavor.client.block_ovelay


import aurocosh.divinefavor.client.render.block_overlay.BlockTemplateBufferBuilder
import aurocosh.divinefavor.common.lib.CustomBufferBuilder
import aurocosh.divinefavor.common.lib.extensions.getPartialPosition
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import java.util.*

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object BlockTemplateRendering {
    private val mc = Minecraft.getMinecraft()

    fun render(lastEvent: RenderWorldLastEvent, player: EntityPlayer, uuid: UUID, origin: BlockPos) {
        val bufferBuilder = BlockTemplateBufferBuilder.getBuffer(uuid, player.world) ?: return

        //Calculate the players current position, which is needed later
        val playerPos = player.getPartialPosition(lastEvent.partialTicks)

        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

        //Save the current position that is being rendered
        GlStateManager.pushMatrix()

        //Enable Blending (So we can have transparent effect)
        GlStateManager.enableBlend()

        //This blend function allows you to use a constant alpha, which is defined later
        GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA)

        GlStateManager.pushMatrix()//Push matrix again just because
        GlStateManager.translate(origin.x - playerPos.x, origin.y - playerPos.y, origin.z - playerPos.z)//Now move the render position to the coordinates we want to render at
        GL14.glBlendColor(1f, 1f, 1f, 0.55f) //Set the alpha of the blocks we are rendering

        GlStateManager.translate(0.0005f, 0.0005f, -0.0005f)
        GlStateManager.scale(1.001f, 1.001f, 1.001f)//Slightly Larger block to avoid z-fighting.
        draw(player, playerPos.x, playerPos.y, playerPos.z, origin, bufferBuilder) //Draw the cached buffer in the world.

        GlStateManager.popMatrix()

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GlStateManager.disableBlend()
        GlStateManager.popMatrix()

    }

    fun draw(player: EntityPlayer, x: Double, y: Double, z: Double, startPos: BlockPos, bufferBuilder: CustomBufferBuilder) {
        //        long time = System.nanoTime();
        bufferBuilder.sortVertexData((x - startPos.x).toFloat(), (y + player.getEyeHeight() - startPos.y).toFloat(), (z - startPos.z).toFloat())
        //System.out.printf("Sorted %d Vertexes in %.2f ms%n", bufferBuilder.getVertexCount(), (System.nanoTime() - time) * 1e-6);
        if (bufferBuilder.vertexCount > 0) {
            val vertexFormat = bufferBuilder.vertexFormat
            val i = vertexFormat.size
            val byteBuffer = bufferBuilder.byteBuffer
            val list = vertexFormat.elements

            for (j in list.indices) {
                val vertexFormatElement = list[j]
                byteBuffer.position(vertexFormat.getOffset(j))
                vertexFormatElement.usage.preDraw(vertexFormat, j, i, byteBuffer)
            }

            GlStateManager.glDrawArrays(bufferBuilder.getDrawMode(), 0, bufferBuilder.getVertexCount())
            var i1 = 0

            val j1 = list.size
            while (i1 < j1) {
                val vertexFormatElement = list[i1]
                vertexFormatElement.usage.postDraw(vertexFormat, i1, i, byteBuffer)
                ++i1
            }
        }
    }
}
