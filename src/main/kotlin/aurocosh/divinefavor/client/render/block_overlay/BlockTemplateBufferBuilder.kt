package aurocosh.divinefavor.client.render.block_overlay

import aurocosh.divinefavor.common.custom_data.global.TemplateData
import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.lib.CustomBufferBuilder
import aurocosh.divinefavor.common.lib.extensions.get
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.util.*
import kotlin.collections.HashMap

@SideOnly(Side.CLIENT)
object BlockTemplateBufferBuilder {
    private val bufferMap: MutableMap<UUID, CustomBufferBuilder> = HashMap()

    // TODO limit buffer size
    fun clearMaps() {
        bufferMap.clear()
    }

    fun getBuffer(uuid: UUID, world: World): CustomBufferBuilder? {
        val bufferBuilder = bufferMap[uuid]
        if (bufferBuilder != null)
            return bufferBuilder

        val templateSavedData = world[TemplateData]
        val compound = templateSavedData.get(uuid) ?: return null
        val newBufferBuilder = buildBuffer(compound, world) ?: return null
        bufferMap[uuid] = newBufferBuilder;
        return newBufferBuilder
    }

    private fun buildBuffer(template: BlockTemplate, world: World): CustomBufferBuilder? {
        //        long time = System.nanoTime();

        val blockMapList = template.getBlockMapList(BlockPos.ORIGIN)
        if (blockMapList.isEmpty())
            return null

        val dispatcher = Minecraft.getMinecraft().blockRendererDispatcher
        val bufferBuilder = CustomBufferBuilder(2097152)
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK)
        for (blockMap in blockMapList) {
            val renderBlockState = blockMap.state
            if (renderBlockState != Blocks.AIR.defaultState) {
                val model = dispatcher.getModelForState(renderBlockState)
                dispatcher.blockModelRenderer.renderModelFlat(world, model, renderBlockState, blockMap.offset, bufferBuilder, false, 0L)
            }
        }
        bufferBuilder.finishDrawing()
        return bufferBuilder
        //System.out.printf("Created %d Vertexes for %d blocks in %.2f ms%n", bufferBuilder.getVertexCount(), blockMapList.size(), (System.nanoTime() - time) * 1e-6);
    }
}
