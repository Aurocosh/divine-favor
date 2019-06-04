package aurocosh.divinefavor.client.render.rope.base

import aurocosh.divinefavor.client.models.rope.base.ModelRopeNode
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.util.UtilLighting
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.GlStateManager.DestFactor
import net.minecraft.client.renderer.GlStateManager.SourceFactor
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

import javax.vecmath.Color4f

open class RenderRopeNodeBase<T : EntityRopeNodeBase>(renderManager: RenderManager) : Render<T>(renderManager) {
    private val frustum: Frustum = Frustum()

    protected open val normalColor: Color4f
        get() = Color4f(1f, 1f, 1f, 0.35f)

    protected open val lastColor: Color4f
        get() = Color4f(0.25f, 1f, 0.25f, 0.35f)

    protected open val connectionColor: Color4f
        get() = Color4f(0.1f, 0.1f, 0.1f, 0.8f)

    protected open val connectionSubColor: Color4f
        get() = Color4f(0.3f, 0.3f, 0.3f, 0.8f)

    override fun doRender(ropeNode: T, x: Double, y: Double, z: Double, yaw: Float, partialTicks: Float) {
        this.bindEntityTexture(ropeNode)

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer

        GlStateManager.pushMatrix()

        GlStateManager.enableBlend()
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()

        val normalColor = normalColor
        GlStateManager.color(normalColor.x, normalColor.y, normalColor.z, normalColor.w)
        UtilLighting.setLighting(255)

        if (ropeNode.nextNodeClient == null) {
            val lastColor = lastColor
            GlStateManager.color(lastColor.x, lastColor.y, lastColor.z, lastColor.w)
        }

        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, z)
        drawModel(ropeNode, partialTicks)
        GlStateManager.popMatrix()

        GlStateManager.color(1f, 1f, 1f, 1f)

        UtilLighting.revert()
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()

        val camPosX = this.interpolate(ropeNode.lastTickPosX, ropeNode.posX, partialTicks.toDouble()) - x
        val camPosY = this.interpolate(ropeNode.lastTickPosY, ropeNode.posY, partialTicks.toDouble()) - y
        val camPosZ = this.interpolate(ropeNode.lastTickPosZ, ropeNode.posZ, partialTicks.toDouble()) - z

        this.frustum.setPosition(camPosX, camPosY, camPosZ)

        val prevNode = ropeNode.previousNodeClient

        if (prevNode != null) {
            if (!this.renderManager.getEntityRenderObject<Entity>(prevNode)!!.shouldRender(prevNode, this.frustum, camPosX, camPosY, camPosZ)) {
                //Previous node not rendered, render rope
                GlStateManager.pushMatrix()
                val renderOffsetX = this.interpolate(prevNode.lastTickPosX - ropeNode.lastTickPosX, prevNode.posX - ropeNode.posX, partialTicks.toDouble())
                val renderOffsetY = this.interpolate(prevNode.lastTickPosY - ropeNode.lastTickPosY, prevNode.posY - ropeNode.posY, partialTicks.toDouble())
                val renderOffsetZ = this.interpolate(prevNode.lastTickPosZ - ropeNode.lastTickPosZ, prevNode.posZ - ropeNode.posZ, partialTicks.toDouble())
                GlStateManager.translate(renderOffsetX, renderOffsetY, renderOffsetZ)
                this.renderConnection(prevNode, ropeNode, tessellator, buffer, x, y, z, partialTicks)
                GlStateManager.popMatrix()
            }
        }

        val nextNode = ropeNode.nextNodeClient
        if (nextNode != null)
            this.renderConnection(ropeNode, nextNode, tessellator, buffer, x, y, z, partialTicks)

        GlStateManager.popMatrix()
    }

    protected open fun drawModel(ropeNode: T, partialTicks: Float) {
        nodeModel.render(ropeNode, 0f, 0f, 0f, 0f, 0f, 0.0625f)
    }

    protected fun interpolate(prev: Double, now: Double, partialTicks: Double): Double {
        return prev + (now - prev) * partialTicks
    }

    protected fun renderConnection(node1: Entity, node2: Entity?, tessellator: Tessellator, buffer: BufferBuilder, x: Double, y: Double, z: Double, partialTicks: Float) {
        if (node2 != null) {
            val camPosX = this.interpolate(node1.prevPosX - x, node1.posX - x, partialTicks.toDouble())
            val camPosY = this.interpolate(node1.prevPosY - y, node1.posY - y, partialTicks.toDouble())
            val camPosZ = this.interpolate(node1.prevPosZ - z, node1.posZ - z, partialTicks.toDouble())

            val endX = this.interpolate(node2.prevPosX - camPosX, node2.posX - camPosX, partialTicks.toDouble())
            var endY = this.interpolate(node2.prevPosY - camPosY, node2.posY - camPosY, partialTicks.toDouble())
            if (node2 !is EntityRopeNodeBase)
                endY += node2.eyeHeight / 2.0
            val endZ = this.interpolate(node2.prevPosZ - camPosZ, node2.posZ - camPosZ, partialTicks.toDouble())

            val diffX = (endX - x).toFloat().toDouble()
            val diffY = (endY - y).toFloat().toDouble()
            val diffZ = (endZ - z).toFloat().toDouble()

            GlStateManager.disableTexture2D()
            GlStateManager.disableLighting()
            GlStateManager.disableCull()

            val connectionColor = connectionColor
            val connectionSubColor = connectionSubColor
            //            buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
            //            for (int i = 0; i <= 24; ++i) {
            //                Color4f currentColor = i % 2 == 0 ? connectionColor : connectionSubColor;
            //
            //                float percentage = (float) i / 24.0F;
            //                double yMult = endY < y ? percentage * Math.sqrt(percentage) : percentage * percentage;
            //
            //                buffer.pos(x + diffX * (double) percentage + 0.0D, y + diffY * (yMult + percentage) * 0.5D, z + diffZ * (double) percentage).color(currentColor.x, currentColor.y, currentColor.z, currentColor.w).endVertex();
            //                buffer.pos(x + diffX * (double) percentage + 0.025D, y + diffY * (yMult + percentage) * 0.5D + 0.025D, z + diffZ * (double) percentage).color(currentColor.x, currentColor.y, currentColor.z, currentColor.w).endVertex();
            //            }
            //            tessellator.draw();

            drawStrip(tessellator, buffer, x, y, z, endY, diffX, diffY, diffZ, connectionColor, connectionSubColor, 0.025, 0.0)
            drawStrip(tessellator, buffer, x, y, z, endY, diffX, diffY, diffZ, connectionColor, connectionSubColor, 0.0, 0.025)

            GlStateManager.enableLighting()
            GlStateManager.enableTexture2D()
            GlStateManager.enableCull()
        }
    }

    private fun drawStrip(tessellator: Tessellator, buffer: BufferBuilder, x: Double, y: Double, z: Double, endY: Double, diffX: Double, diffY: Double, diffZ: Double, connectionColor: Color4f, connectionSubColor: Color4f, firstShift: Double, secondShift: Double) {
        buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR)
        for (i in 0..24) {
            val currentColor = if (i % 2 == 0) connectionColor else connectionSubColor
            val percentage = i.toDouble() / 24.0f
            val yMult = if (endY < y) percentage * Math.sqrt(percentage) else percentage * percentage

            buffer.pos(x + diffX * percentage + 0.0, y + diffY * (yMult + percentage) * 0.5 + firstShift, z + diffZ * percentage).color(currentColor.x, currentColor.y, currentColor.z, currentColor.w).endVertex()
            buffer.pos(x + diffX * percentage + 0.025, y + diffY * (yMult + percentage) * 0.5 + secondShift, z + diffZ * percentage + 0.025).color(currentColor.x, currentColor.y, currentColor.z, currentColor.w).endVertex()
        }
        tessellator.draw()
    }

    override fun getEntityTexture(p0: T): ResourceLocation? {
        return TEXTURE
    }

    companion object {

        protected val TEXTURE = ResourceLocation("divinefavor:textures/blocks/rope_explosive.png")
        protected val nodeModel = ModelRopeNode()
    }
}