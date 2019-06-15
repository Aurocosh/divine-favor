package aurocosh.divinefavor.client.block_ovelay

import aurocosh.divinefavor.common.lib.extensions.getPartialPosition
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.block.BlockStainedGlass.COLOR
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.EnumDyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.WorldType
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object BlockConstructionRendering {
    private val fakeWorld = FakeBuilderWorld()
    private val mc = Minecraft.getMinecraft()
    private val missingBlockMarker = Blocks.STAINED_GLASS.defaultState.withProperty(COLOR, EnumDyeColor.RED)

    fun render(lastEvent: RenderWorldLastEvent, player: EntityPlayer, state: IBlockState, coordinates: List<BlockPos>) {
        var renderState = state
        if (renderState === Blocks.AIR.defaultState)
            return
        if (coordinates.isEmpty())
            return

        // Figure out how many of the block we're rendering are in the player inventory.
        val itemStack = UtilBlock.getSilkDropIfPresent(player.world, renderState, player)

        // Check if we have the blocks required
        var blockCount = UtilPlayer.countItems(itemStack, player)

        // Prepare the fake world -- using a fake world lets us render things properly, like fences connecting.
        fakeWorld.setWorldAndState(player.world, renderState, coordinates.toHashSet())

        GlStateManager.pushMatrix()
        Utils.stateManagerPrepareBlend()

        val sortedCoordinates = Sorter.Blocks.byDistance(coordinates, player) //Sort the coords by distance to player.

        // Calculate the players current position, which is needed later
        val playerPos = player.getPartialPosition(lastEvent.partialTicks)
        for (coordinate in sortedCoordinates) {
            GlStateManager.pushMatrix()
            Utils.stateManagerPrepare(playerPos, coordinate, null)
            GL14.glBlendColor(1f, 1f, 1f, 0.55f) //Set the alpha of the blocks we are rendering

            if (fakeWorld.worldType !== WorldType.DEBUG_ALL_BLOCK_STATES)
                renderState = renderState.getActualState(fakeWorld, coordinate)

            mc.blockRendererDispatcher.renderBlockBrightness(renderState, 1f)//Render the defined block
            GlStateManager.popMatrix()

            GlStateManager.pushMatrix()
            Utils.stateManagerPrepare(playerPos, coordinate, 0.01f)
            GlStateManager.scale(1.006f, 1.006f, 1.006f)
            GL14.glBlendColor(1f, 1f, 1f, 0.35f)

            blockCount--

            if (blockCount < 0)
                mc.blockRendererDispatcher.renderBlockBrightness(missingBlockMarker, 1f)

            // Move the render position back to where it was
            GlStateManager.popMatrix()
        }

        //Set blending back to the default mode
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer())
        //Disable blend
        GlStateManager.disableBlend()
        //Pop from the original push in this method
        GlStateManager.popMatrix()
    }

    private object Utils {
        fun stateManagerPrepareBlend() {
            GlStateManager.enableBlend()
            GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA)
        }

        /**
         * Prepares our render using base properties
         */
        fun stateManagerPrepare(playerPos: Vec3d, blockPos: BlockPos, shift: Float?) {
            GlStateManager.translate(blockPos.x - playerPos.x, blockPos.y - playerPos.y, blockPos.z - playerPos.z)//Now move the render position to the coordinates we want to render at
            // Rotate it because i'm not sure why but we need to
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f)
            GlStateManager.scale(1f, 1f, 1f)

            // Slightly Larger block to avoid z-fighting.
            if (shift != null) {
                GlStateManager.translate(-shift, -shift, shift)
                GlStateManager.scale(1.005f, 1.005f, 1.005f)
            }
        }
    }
}
