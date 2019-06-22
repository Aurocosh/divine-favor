package aurocosh.divinefavor.client.block_ovelay


import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.getPartialPosition
import com.google.common.cache.CacheBuilder
import com.google.common.cache.RemovalNotification
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import javax.vecmath.Color3f

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object BlockHighlightRendering {
    private val mc = Minecraft.getMinecraft()
    private val cacheDestructionOverlay = CacheBuilder
            .newBuilder()
            .maximumSize(1)
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .removalListener { removal: RemovalNotification<Pair<List<Any>,Color3f>, Int> ->
                GLAllocation.deleteDisplayLists(removal.value)
            }.build<Pair<List<Any>,Color3f>, Int>();

    fun render(lastEvent: RenderWorldLastEvent, player: EntityPlayer, coordinates: List<BlockPos>, color: Color3f = Color3f(1f, 0f, 0f)) {
        val unmodifiableList = Collections.unmodifiableList(coordinates)
        val key = Pair(unmodifiableList, color)

        val playerPos = player.getPartialPosition(lastEvent.partialTicks)
        GlStateManager.pushMatrix()
        GlStateManager.translate(-playerPos.x, -playerPos.y, -playerPos.z)
        try {
            GlStateManager.callList(cacheDestructionOverlay.get(key) {
                val displayList = GLAllocation.generateDisplayLists(1)
                GlStateManager.glNewList(displayList, GL11.GL_COMPILE)
                render(player, coordinates, color)
                GlStateManager.glEndList()
                displayList
            })
        } catch (e: ExecutionException) {
            DivineFavor.logger.error("Error encountered while rendering destruction gadget overlay", e)
        }

        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
    }

    private fun render(player: EntityPlayer, coordinates: List<BlockPos>, color: Color3f) {
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

        GlStateManager.pushMatrix()
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)

        val sortedCoordinates = Sorter.Blocks.byDistance(coordinates, player) //Sort the coords by distance to player.

        val t = Tessellator.getInstance()
        val bufferBuilder = t.buffer

        for (coordinate in sortedCoordinates) {
            var invisible = true
            val state = player.world.getBlockState(coordinate)
            for (side in EnumFacing.values()) {
                if (state.shouldSideBeRendered(player.world, coordinate, side)) {
                    invisible = false
                    break
                }
            }

            if (invisible)
                continue

            GlStateManager.pushMatrix()//Push matrix again just because
            GlStateManager.translate(coordinate.x.toFloat(), coordinate.y.toFloat(), coordinate.z.toFloat())//Now move the render position to the coordinates we want to render at
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f) //Rotate it because i'm not sure why but we need to
            GlStateManager.translate(-0.005f, -0.005f, 0.005f)
            GlStateManager.scale(1.01f, 1.01f, 1.01f)//Slightly Larger block to avoid z-fighting.

            GlStateManager.disableLighting()
            GlStateManager.disableTexture2D()

            renderBoxSolid(t, bufferBuilder, 0.0, 0.0, -1.0, 1.0, 1.0, 0.0, color.x, color.y, color.z, 0.5f)

            GlStateManager.enableTexture2D()
            GlStateManager.enableLighting()
            GlStateManager.popMatrix()
        }

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer())

        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }

    private fun renderBoxSolid(tessellator: Tessellator, bufferBuilder: BufferBuilder, startX: Double, startY: Double, startZ: Double, endX: Double, endY: Double, endZ: Double, red: Float, green: Float, blue: Float, alpha: Float) {
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR)

        //down
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()

        //up
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()

        //east
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()

        //west
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()

        //south
        bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()

        //north
        bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()
        bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
        tessellator.draw()
    }
}
