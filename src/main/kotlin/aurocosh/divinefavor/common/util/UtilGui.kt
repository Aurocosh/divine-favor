package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.math.Vector2i
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.util.*

object UtilGui {
    @SideOnly(Side.CLIENT)
    fun drawPolyline(points: List<Vector2i>, red: Float, green: Float, blue: Float, alpha: Float) {
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GlStateManager.disableDepth()
        GL11.glLineWidth(2f)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST)
        GL11.glBegin(GL11.GL_LINE_STRIP)
        GL11.glColor4f(red, green, blue, alpha)

        for (i in points.indices) {
            val (x, y) = points[i]
            GlStateManager.glVertex3f(x.toFloat(), y.toFloat(), 150f)
        }

        GL11.glEnd()
        GlStateManager.enableDepth()
        GL11.glEnable(GL11.GL_TEXTURE_2D)
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

    fun findClosestPoint(target: Vector2i, points: Collection<Vector2i>, defaultValue: Vector2i): Vector2i {
        var result = defaultValue
        var maxDistanceSq = Integer.MAX_VALUE
        for (point in points) {
            val distanceSq = target.subtract(point).magnitudeSquare()
            if (distanceSq < maxDistanceSq) {
                maxDistanceSq = distanceSq
                result = point
            }
        }
        return result
    }

    fun findClosestPointIndex(target: Vector2i, points: List<Vector2i>, defaultValue: Int): Int {
        var result = defaultValue
        var maxDistanceSq = Integer.MAX_VALUE
        for (i in points.indices) {
            val distanceSq = target.subtract(points[i]).magnitudeSquare()
            if (distanceSq < maxDistanceSq) {
                maxDistanceSq = distanceSq
                result = i
            }
        }
        return result
    }

    fun generateSpiral(pointCount: Int, pointsToSkip: Int, scalar: Int, a: Double, aChange: Double, xScale: Double, yScale: Double): List<Vector2i> {
        var aVar = a
        val points = ArrayList<Vector2i>(pointCount)
        for (i in 0 until pointCount) {
            //find `ln(d*r)/a` for both points
            val lni1 = Math.log(aVar * scalar.toDouble() * (i + pointsToSkip).toDouble()) / aVar
            val logSpiral = Math.exp(lni1 * aVar)
            val pointX = xScale * logSpiral * Math.cos(lni1)
            val pointY = yScale * logSpiral * Math.sin(lni1)

            points.add(Vector2i(pointX.toInt(), pointY.toInt()))

            aVar += aChange
        }
        return points
    }

    @SideOnly(Side.CLIENT)
    fun drawTexture(mc: Minecraft, location: ResourceLocation, x: Int, y: Int, width: Int, height: Int) {
        mc.textureManager.bindTexture(location)
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, width, height, width.toFloat(), height.toFloat())
    }
}
