package aurocosh.divinefavor.client.gui.items

import aurocosh.divinefavor.client.render.block_overlay.BlockTemplateBufferBuilder
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.item.memory_pouch.MemoryPouchTemplateContainer
import aurocosh.divinefavor.common.item.memory_pouch.capability.IMemoryPouch
import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.isInvalid
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiTextField
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Rectangle
import org.lwjgl.util.glu.Project
import java.util.*

class GuiMemoryPouchTemplate(val player: EntityPlayer, internal var stack: ItemStack, hand: EnumHand, private val pouch: IMemoryPouch = stack.cap(CAPABILITY_MEMORY_POUCH))
    : GuiContainer(MemoryPouchTemplateContainer(player, pouch, hand)) {

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
    private var prevRotY: Float = 0f// prevPanX, prevPanY;
    private var momentumX: Float = 0f
    private var momentumY: Float = 0f
    private val momentumDampening = 0.98f
    private var rotX = 0f
    private var rotY = 0f
    private var zoom = defaultZoom
    private var panX = 0f
    private var panY = 0f

    private lateinit var nameField: GuiTextField
    private val panel = Rectangle(8, 18, 160, 95)

    override fun initGui() {
        super.initGui()
        xSize = WIDTH
        ySize = HEIGHT

        nameField = GuiTextField(0, fontRenderer, guiLeft + 8, guiTop + 6, 160, fontRenderer.FONT_HEIGHT)
        nameField.maxStringLength = 50
        nameField.visible = true

    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        mc.textureManager.bindTexture(texture)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)

        nameField.drawTextBox()
        drawStructure()
    }

    private fun isMouseOverSlot(slotIn: Slot, mouseX: Int, mouseY: Int): Boolean {
        return isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY)
    }


    private fun drawStructure() {
        val scale = ScaledResolution(mc).scaleFactor
        Gui.drawRect(guiLeft + panel.x - 1, guiTop + panel.y - 1, guiLeft + panel.x + panel.width + 1, guiTop + panel.y + panel.height + 1, -0x757576)

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)

        //float rotX = 165, rotY = 0, zoom = 1;
        val uuid = getTemplate()
        if (uuid.isInvalid()) {
            resetStructureView()
            return
        }


        val world = player.world
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

        //double sc = 300 + 8 * 0.0125 * (Math.sqrt(zoom + 99) - 9);
        sc = 293 * sc + zoom / zoomScale
        GlStateManager.scale(sc, sc, sc)
        var moveX = startPos.x - endPos.x

        //GlStateManager.rotate(30, 0, 1, 0);
        if (startPos.x >= endPos.x) {
            moveX--
            //GlStateManager.rotate(90, 0, -1, 0);
        }

        GlStateManager.translate(moveX / 1.75, -Math.abs(startPos.y - endPos.y) / 1.75, 0.0)
        GlStateManager.translate(panX, panY, 0f)
        //System.out.println(((startPos.getX() - endPos.getX()) / 2) * -1 + ":" + ((startPos.getY() - endPos.getY()) / 2) * -1 + ":" + ((startPos.getZ() - endPos.getZ()) / 2) * -1);
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

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (!nameField.textboxKeyTyped(typedChar, keyCode))
            super.keyTyped(typedChar, keyCode)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (nameField.mouseClicked(mouseX, mouseY, mouseButton)) {
            nameField.isFocused = true
        } else {
            nameField.isFocused = false
            if (panel.contains(mouseX - guiLeft, mouseY - guiTop)) {
                clickButton = mouseButton
                panelClicked = true
                clickX = Mouse.getX()
                clickY = Mouse.getY()
            }
            super.mouseClicked(mouseX, mouseY, mouseButton)
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
        panelClicked = false
        initRotX = rotX
        initRotY = rotY
        initPanX = panX
        initPanY = panY
        initZoom = zoom
    }

    override fun drawGuiContainerForegroundLayer(j: Int, i: Int) {
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

        if (!nameField.isFocused && nameField.text.isEmpty())
            fontRenderer.drawString("template name", nameField.x - guiLeft + 4, nameField.y - guiTop, -10197916)
    }

    override fun handleMouseInput() {
        super.handleMouseInput()
        val dWheel = Mouse.getEventDWheel()
        if (dWheel != 0) {
            val zoomChange = dWheel / 2
            zoom = MathHelper.clamp(zoom + zoomChange, -200f, 1000f)
        }
    }

    private fun getTemplate(): UUID {
        return player.divinePlayerData.templateData.currentTemplate
    }

    companion object {
        const val WIDTH = 175
        const val HEIGHT = 178
        const val defaultZoom = -80f
        private val texture = ResourceLocation(ConstResources.GUI_MEMORY_POUCH_TEMPLATE)
    }
}
