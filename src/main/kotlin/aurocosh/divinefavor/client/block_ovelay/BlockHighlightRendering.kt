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
import net.minecraft.util.math.Vec3d
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
    private val sides = EnumFacing.values()
    private val mc = Minecraft.getMinecraft()
    private val cacheDestructionOverlay = CacheBuilder
            .newBuilder()
            .maximumSize(1)
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .removalListener { removal: RemovalNotification<Pair<List<Any>, Color3f>, Int> ->
                GLAllocation.deleteDisplayLists(removal.value)
            }.build<Pair<List<Any>, Color3f>, Int>();

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

        val tessellator = Tessellator.getInstance()
        val bufferBuilder = tessellator.buffer

        val world = player.world
        for (coordinate in sortedCoordinates) {
            val state = world.getBlockState(coordinate)
            val sideToDraw = sides.filter { state.shouldSideBeRendered(world,coordinate,it) }
            if(sideToDraw.isEmpty())
                continue

            GlStateManager.pushMatrix()//Push matrix again just because
            GlStateManager.translate(coordinate.x.toFloat(), coordinate.y.toFloat(), coordinate.z.toFloat())//Now move the render position to the coordinates we want to render at

            GlStateManager.disableLighting()
            GlStateManager.disableTexture2D()

            for (facing in sideToDraw) {
                val shift = getSideShift(facing)
                GlStateManager.translate(shift.x, shift.y, shift.z)
                renderBoxSide(tessellator, bufferBuilder, facing, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, color.x, color.y, color.z, 0.5f)
                GlStateManager.translate(-shift.x, -shift.y, -shift.z)
            }

            GlStateManager.enableTexture2D()
            GlStateManager.enableLighting()
            GlStateManager.popMatrix()
        }

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer())

        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }

    private fun getSideShift(facing: EnumFacing): Vec3d {
        return when (facing) {
            EnumFacing.DOWN -> Vec3d(0.0, -0.0001, 0.0)
            EnumFacing.UP -> Vec3d(0.0, 0.0001, 0.0)
            EnumFacing.NORTH -> Vec3d(0.0, 0.0, -0.0001)
            EnumFacing.SOUTH -> Vec3d(0.0, 0.0, 0.0001)
            EnumFacing.WEST -> Vec3d(-0.0001, 0.0, 0.0)
            EnumFacing.EAST -> Vec3d(0.0001, 0.0, 0.0)
        }
    }

    private fun renderBoxSide(tessellator: Tessellator, bufferBuilder: BufferBuilder, facing: EnumFacing, startX: Double, startY: Double, startZ: Double, endX: Double, endY: Double, endZ: Double, red: Float, green: Float, blue: Float, alpha: Float) {
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR)
        when (facing) {
            EnumFacing.DOWN -> {
                bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()
            }
            EnumFacing.UP -> {
                bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()
            }
            EnumFacing.NORTH -> {
                bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()
            }
            EnumFacing.SOUTH -> {
                bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()
            }
            EnumFacing.WEST -> {
                //fixed
                bufferBuilder.pos(startX, startY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, startY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, endY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(startX, endY, startZ).color(red, green, blue, alpha).endVertex()
            }
            EnumFacing.EAST -> {
                //fixed
                bufferBuilder.pos(endX, startY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, startZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, endY, endZ).color(red, green, blue, alpha).endVertex()
                bufferBuilder.pos(endX, startY, endZ).color(red, green, blue, alpha).endVertex()
            }
        }
        tessellator.draw()
    }
}
