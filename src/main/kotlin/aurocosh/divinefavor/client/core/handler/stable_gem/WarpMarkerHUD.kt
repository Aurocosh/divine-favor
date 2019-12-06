package aurocosh.divinefavor.client.core.handler.stable_gem

import aurocosh.divinefavor.common.item.gems.base.IUsableGemItem
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import com.google.common.cache.CacheBuilder
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiIngame
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.ReflectionHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11

object WarpMarkerHUD {
    private val stackChache = CacheBuilder
            .newBuilder()
            .maximumSize(64)
            .build<String, ItemStack>();

    private val REMAINING_HIGHLIGHT_TICKS_INDEX = 12

    fun drawGemDescription(mc: Minecraft, width: Int, height: Int, player: EntityPlayer, stack: ItemStack, drawName: Boolean) {
        if (stack.isEmpty)
            return

        val gemItem = stack.item as IUsableGemItem
        val description = getUseInfo(player, stack, gemItem)

        val spirit = gemItem.spirit
        val spiritData = player.divinePlayerData.spiritData

        val maskStack = getMaskStack(stack)

        val favorDescription =
                when {
                    gemItem.consumeOnUse -> I18n.format("divinefavor:cost_no_favor")
                    spiritData.isFavorInfinite(spirit.id) -> I18n.format("divinefavor:favor_infinite")
                    else -> {
                        val value = spiritData.getFavor(spirit.id)
                        val maxLimit = spiritData.getMaxFavor(spirit.id)
                        "$value/$maxLimit"
                    }
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
            val translatedName = I18n.format(stack.item.translationKey)
            mc.fontRenderer.drawStringWithShadow(translatedName, 24f, -10f, color)
        }

        mc.fontRenderer.drawStringWithShadow(description, 24f, 0f, color)

        val favorDescriptionWidth = mc.fontRenderer.getStringWidth(favorDescription)
        mc.fontRenderer.drawStringWithShadow(favorDescription, (-favorDescriptionWidth - 22).toFloat(), 0f, color)

        // talisman icon
        GlStateManager.scale(alpha / 255f, 1f, 1f)
        GlStateManager.color(1f, 1f, 1f)
        mc.renderItem.renderItemIntoGUI(maskStack, 5, -6)

        // spirit icon
        mc.renderEngine.bindTexture(spirit.icon)
        Gui.drawModalRectWithCustomSizedTexture(-21, -6, 0f, 0f, 16, 16, 16f, 16f)

        // spirit symbol
        mc.renderEngine.bindTexture(spirit.symbol)
        Gui.drawModalRectWithCustomSizedTexture(-21, -6, 0f, 0f, 16, 16, 16f, 16f)

        GlStateManager.popMatrix()
        GlStateManager.disableBlend()
    }

    @SideOnly(Side.CLIENT)
    fun getUseInfo(player: EntityPlayer, stack: ItemStack, gem: IUsableGemItem): String {
        val useCount = if (gem.consumeOnUse) stack.count
        else {
            val spiritData = player.divinePlayerData.spiritData
            val favorValue = spiritData.getFavor(gem.spirit.id)

            val infinite = spiritData.isFavorInfinite(gem.spirit.id) || gem.favorCost == 0
            if (infinite) -1 else favorValue / gem.favorCost
        }

        return when {
            gem.consumeOnUse -> I18n.format("tooltip.divinefavor:gem.consume_uses", useCount)
            useCount < 0 -> I18n.format("tooltip.divinefavor:gem.infinite_use")
            useCount == 0 -> I18n.format("tooltip.divinefavor:gem.unusable")
            else -> I18n.format("tooltip.divinefavor:gem.cost", gem.favorCost, useCount)
        }
    }

    fun getMaskStack(stack: ItemStack): ItemStack {
        val itemId = stack.get(GemMaskProperties.maskItemId)
        val itemMeta = stack.get(GemMaskProperties.maskItemMeta)

        val item = Item.getByNameOrId(itemId) ?: return stack
        val key = itemId + itemMeta;

        return stackChache.get(key) { ItemStack(item,1,itemMeta) }
    }
}
