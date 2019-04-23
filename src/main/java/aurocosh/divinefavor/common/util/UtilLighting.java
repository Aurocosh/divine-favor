package aurocosh.divinefavor.common.util;

import net.minecraft.client.renderer.OpenGlHelper;

public class UtilLighting {
    public static final UtilLighting INSTANCE = new UtilLighting();

    private float lastX, lastY;

    /**
     * Sets the currently used lighting (0 - 255).
     * Use {@link UtilLighting#revert()} after rendering to revert to previous lighting.
     *
     * @param lighting
     */
    public void setLighting(int lighting) {
        this.lastX = OpenGlHelper.lastBrightnessX;
        this.lastY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 238.0f / 255.0f * lighting, 238.0f / 255.0f * lighting);
    }

    /**
     * Reverts the lighting to the previous state.
     */
    public void revert() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, this.lastX, this.lastY);
    }
}
