package aurocosh.divinefavor.client.block_ovelay

import aurocosh.divinefavor.common.lib.extensions.getPartialPosition
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilBlockRendering
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.block.BlockStainedGlass.COLOR
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.EnumDyeColor
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.math.BlockPos
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

object BlockExchangeRendering {
    private val fakeWorld = FakeBuilderWorld()
    private val mc = Minecraft.getMinecraft()
    private val stainedGlassWhite = Blocks.STAINED_GLASS.defaultState.withProperty(COLOR, EnumDyeColor.WHITE)
    private val stainedGlassRed = Blocks.STAINED_GLASS.defaultState.withProperty(COLOR, EnumDyeColor.RED)

    fun render(lastEvent: RenderWorldLastEvent, player: EntityPlayer, state: IBlockState, coordinates: List<BlockPos>) {
        val dispatcher = mc.blockRendererDispatcher

        var stateA = Blocks.AIR.defaultState

        if (coordinates.isEmpty())
            return

        val renderBlockState = state
        val mc = Minecraft.getMinecraft()
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
        
        //Don't render anything if there is no block selected (Air)
        if (renderBlockState === Blocks.AIR.defaultState)
            return

        // Figure out how many of the block we're rendering are in the player inventory.
        val itemStack = UtilBlock.getSilkDropIfPresent(player.world, renderBlockState, player)

        // Check if we have the blocks required
        var blockCount = UtilPlayer.countItems(itemStack, player)
        blockCount += UtilPlayer.countGoo(player)

        // Prepare the fake world -- using a fake world lets us render things properly, like fences connecting.
        fakeWorld.setWorldAndState(player.world, renderBlockState, coordinates.toHashSet())

        // Calculate the players current position, which is needed later
        val playerPos = player.getPartialPosition(lastEvent.partialTicks)

        GlStateManager.pushMatrix()
        UtilBlockRendering.stateManagerPrepareBlend()

        for (coordinate in coordinates) {
            GlStateManager.pushMatrix()
            UtilBlockRendering.stateManagerPrepare(playerPos, coordinate, 0.001f)
            GL14.glBlendColor(1f, 1f, 1f, 0.55f) //Set the alpha of the blocks we are rendering

            // Get the block state in the fake world
            if (fakeWorld.worldType !== WorldType.DEBUG_ALL_BLOCK_STATES)
                stateA = renderBlockState.getActualState(fakeWorld, coordinate)

            if (renderBlockState.renderType != EnumBlockRenderType.INVISIBLE) {
                dispatcher.renderBlockBrightness(stateA, 1f)//Render the defined block
                GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f) //Rotate it because i'm not sure why but we need to
            }

            GL14.glBlendColor(1f, 1f, 1f, 0.1f) //Set the alpha of the blocks we are rendering
            dispatcher.renderBlockBrightness(stainedGlassWhite, 1f)//Render the defined block - White glass to show non-full block renders (Example: Torch)
            GlStateManager.popMatrix()

            GlStateManager.pushMatrix()
            UtilBlockRendering.stateManagerPrepare(playerPos, coordinate, 0.002f)

            GlStateManager.scale(1.02f, 1.02f, 1.02f) //Slightly Larger block to avoid z-fighting.
            GL14.glBlendColor(1f, 1f, 1f, 0.55f) //Set the alpha of the blocks we are rendering
            blockCount--

            if (blockCount < 0)
                dispatcher.renderBlockBrightness(stainedGlassRed, 1f)

            // Move the render position back to where it was
            GlStateManager.popMatrix()
        }

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        ForgeHooksClient.setRenderLayer(MinecraftForgeClient.getRenderLayer())

        GlStateManager.disableBlend()
        GlStateManager.popMatrix()
    }
}
