package aurocosh.divinefavor.client.core.handler.talisman

import aurocosh.divinefavor.common.item.base.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiIngame
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.ReflectionHelper
import org.lwjgl.opengl.GL11

object TalismanHUD {
    private val REMAINING_HIGHLIGHT_TICKS_INDEX = 12

    fun drawTalismanDescription(mc: Minecraft, width: Int, height: Int, player: EntityPlayer, talismanStack: ItemStack, drawName: Boolean) {
        if (talismanStack.isEmpty)
            return

        val talisman = talismanStack.item as ItemTalisman
        val description = talisman.getUseInfo(player)

        val spirit = talisman.spirit
        val spiritData = player.divinePlayerData.spiritData

        val favorDescription =
                if (spiritData.isFavorInfinite(spirit.id))
                    I18n.format("divinefavor:favor_infinite")
                else {
                    val value = spiritData.getFavor(spirit.id)
                    val maxLimit = spiritData.getMaxFavor(spirit.id)
                    "$value/$maxLimit"
                }

        val alpha = 255
        val color = (0 shl 0) + (128 shl 8) + (0 shl 16) + (alpha shl 24)

        val x = width / 2
        var y = height - 71

        val ticks = ReflectionHelper.getPrivateValue<Int, GuiIngame>(GuiIngame::class.java, mc.ingameGUI, REMAINING_HIGHLIGHT_TICKS_INDEX)
        //        ticks -= 10;
        if (ticks <= 0)
            y += 12

        if (mc.player.capabilities.isCreativeMode)
            y += 14

        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.pushMatrix()
        GlStateManager.translate(x.toFloat(), y.toFloat(), 0f)

        if (drawName) {
            val translatedName = I18n.format(talisman.nameKey)
            mc.fontRenderer.drawStringWithShadow(translatedName, 24f, -10f, color)
        }

        mc.fontRenderer.drawStringWithShadow(description, 24f, 0f, color)

        val favorDescriptionWidth = mc.fontRenderer.getStringWidth(favorDescription)
        mc.fontRenderer.drawStringWithShadow(favorDescription, (-favorDescriptionWidth - 22).toFloat(), 0f, color)

        // talisman icon
        GlStateManager.scale(alpha / 255f, 1f, 1f)
        GlStateManager.color(1f, 1f, 1f)
        mc.renderItem.renderItemIntoGUI(talismanStack, 5, -6)

        // spirit icon
        mc.renderEngine.bindTexture(spirit.icon)
        Gui.drawModalRectWithCustomSizedTexture(-21, -6, 0f, 0f, 16, 16, 16f, 16f)

        // spirit symbol
        mc.renderEngine.bindTexture(spirit.symbol)
        Gui.drawModalRectWithCustomSizedTexture(-21, -6, 0f, 0f, 16, 16, 16f, 16f)

        GlStateManager.popMatrix()
        GlStateManager.disableBlend()
    }
}
