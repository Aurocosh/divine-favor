package aurocosh.divinefavor.common.util

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.lwjgl.opengl.GL11

/**
 * Parts of this class were adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

object UtilBlockRendering {
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
