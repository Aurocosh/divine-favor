package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.client.render.block_overlay.BlockTemplateBufferBuilder
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isInvalid
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Rectangle
import org.lwjgl.util.glu.Project
import java.util.*
import kotlin.math.sign

class GuiBlockTemplateView(private val world: World, private val panel: Rectangle, private val mc: Minecraft, private val guiTop: Int, private val guiLeft: Int, private val scrollSpeed: Int)
    : Gui() {

    private var panelClicked: Boolean = false
    private var clickButton: Int = 0

    private var clickX: Int = 0
    private var clickY: Int = 0

    private var initRotX: Float = 0f
    private var initRotY: Float = 0f
    private var initZoom: Float = defaultZoom
    private var initPanX: Float = 0f
    private var initPanY: Float = 0f

    private var prevRotX: Float = 0f
    private var prevRotY: Float = 0f

    private var momentumX: Float = 0f
    private var momentumY: Float = 0f

    private val momentumDampening = 0.98f

    private var rotX = 0f
    private var rotY = 0f
    private var zoom = defaultZoom
    private var panX = 0f
    private var panY = 0f

    fun drawStructure(uuid: UUID) {
        val scale = ScaledResolution(mc).scaleFactor
        drawRect(guiLeft + panel.x - 1, guiTop + panel.y - 1, guiLeft + panel.x + panel.width + 1, guiTop + panel.y + panel.height + 1, -0x757576)

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

        //float rotX = 165, rotY = 0, zoom = 1;
        if (uuid.isInvalid()) {
            resetStructureView()
            return
        }

        val bufferBuilder = BlockTemplateBufferBuilder.getBuffer(uuid, world) ?: return
        val template = world[TemplateData][uuid] ?: return

        val boundingBox = template.boundingBox

        val startPos = boundingBox.lowerCorner
        val endPos = boundingBox.upperCorner

        val lengthX = boundingBox.sizeX
        val lengthY = boundingBox.sizeY
        val lengthZ = boundingBox.sizeZ

        val maxW = (6 * 16).toDouble()
        val maxH = (11 * 16).toDouble()

        val overW = Math.max(lengthX * 16 - maxW, lengthZ * 16 - maxW)
        val overH = lengthY * 16 - maxH

        var sc = 1.0
        var zoomScale = 1.0

        if (overW > 0 && overW >= overH) {
            sc = maxW / (overW + maxW)
            zoomScale = overW / 40
        } else if (overH > 0 && overH >= overW) {
            sc = maxH / (overH + maxH)
            zoomScale = overH / 40
        }

        GlStateManager.pushMatrix()

        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()
        //int scale = new ScaledResolution(mc).getScaleFactor();
        Project.gluPerspective(60f, panel.width.toFloat() / panel.height, 0.01f, 4000f)
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        //GlStateManager.translate(-panel.getX() - panel.getWidth() / 2, -panel.getY() - panel.getHeight() / 2, 0);
        GlStateManager.viewport((guiLeft + panel.x) * scale, mc.displayHeight - (guiTop + panel.y + panel.height) * scale, panel.width * scale, panel.height * scale)
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT)

        sc = 293 * sc + zoom / zoomScale
        GlStateManager.scale(sc, sc, sc)
        var moveX = startPos.x - endPos.x

        if (startPos.x >= endPos.x) {
            moveX--
        }

        GlStateManager.translate(moveX / 1.75, -Math.abs(startPos.y - endPos.y) / 1.75, 0.0)
        GlStateManager.translate(panX, panY, 0f)
        GlStateManager.translate(((startPos.x - endPos.x) / 2 * -1).toFloat(), ((startPos.y - endPos.y) / 2 * -1).toFloat(), ((startPos.z - endPos.z) / 2 * -1).toFloat())
        GlStateManager.rotate(rotX, 1f, 0f, 0f)
        GlStateManager.rotate(rotY, 0f, 1f, 0f)
        GlStateManager.translate(((startPos.x - endPos.x) / 2).toFloat(), ((startPos.y - endPos.y) / 2).toFloat(), ((startPos.z - endPos.z) / 2).toFloat())

        mc.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

        if (bufferBuilder.vertexCount > 0) {
            val vertexFormat = bufferBuilder.vertexFormat
            val i = vertexFormat.size
            val byteBuffer = bufferBuilder.byteBuffer
            val list = vertexFormat.elements

            for (j in list.indices) {
                val formatElement = list[j]
                byteBuffer.position(vertexFormat.getOffset(j))

                // moved to VertexFormatElement.preDraw
                formatElement.usage.preDraw(vertexFormat, j, i, byteBuffer)
            }

            GlStateManager.glDrawArrays(bufferBuilder.drawMode, 0, bufferBuilder.vertexCount)
            var i1 = 0

            val j1 = list.size
            while (i1 < j1) {
                val formatElement = list[i1]

                // moved to VertexFormatElement.postDraw
                formatElement.usage.postDraw(vertexFormat, i1, i, byteBuffer)
                ++i1
            }
        }

        GlStateManager.popMatrix()
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.popMatrix()
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight)
    }

    private fun resetStructureView() {
        rotX = 0f
        rotY = 0f
        zoom = defaultZoom
        momentumX = 0f
        momentumY = 0f
        panX = 0f
        panY = 0f
    }

    fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (panel.contains(mouseX - guiLeft, mouseY - guiTop)) {
            clickButton = mouseButton
            panelClicked = true
            clickX = Mouse.getX()
            clickY = Mouse.getY()
        }
    }

    fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        panelClicked = false
        initRotX = rotX
        initRotY = rotY
        initPanX = panX
        initPanY = panY
        initZoom = zoom
    }

    fun drawGuiContainerForegroundLayer(j: Int, i: Int) {
        var doMomentum = false
        if (panelClicked) {
            when (clickButton) {
                0 -> {
                    prevRotX = rotX
                    prevRotY = rotY
                    rotX = initRotX - (Mouse.getY() - clickY)
                    rotY = initRotY + (Mouse.getX() - clickX)
                    momentumX = rotX - prevRotX
                    momentumY = rotY - prevRotY
                    doMomentum = false
                }
                1 -> {
                    panX = initPanX + (Mouse.getX() - clickX) / 8
                    panY = initPanY + (Mouse.getY() - clickY) / 8
                }
                2 -> resetStructureView()
            }
        }

        if (doMomentum) {
            rotX += momentumX
            rotY += momentumY
            momentumX *= momentumDampening
            momentumY *= momentumDampening
        }
    }

    fun handleMouseInput() {
        val dWheel = Mouse.getEventDWheel()
        if (dWheel != 0) {
            val scroll = sign(dWheel.toFloat()) * scrollSpeed
            zoom = MathHelper.clamp(zoom + scroll, -200f, 1000f)
        }
    }

    companion object {
        const val defaultZoom = -80f
    }
}
