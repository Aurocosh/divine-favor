package aurocosh.divinefavor.common.util

import net.minecraft.client.renderer.OpenGlHelper

object UtilLighting {
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()

    /**
     * Sets the currently used lighting (0 - 255).
     * Use [UtilLighting.revert] after rendering to revert to previous lighting.
     *
     * @param lighting
     */
    fun setLighting(lighting: Int) {
        lastX = OpenGlHelper.lastBrightnessX
        lastY = OpenGlHelper.lastBrightnessY
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 238.0f / 255.0f * lighting, 238.0f / 255.0f * lighting)
    }

    /**
     * Reverts the lighting to the previous state.
     */
    fun revert() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY)
    }
}
