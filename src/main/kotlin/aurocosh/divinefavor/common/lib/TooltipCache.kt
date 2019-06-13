package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.util.UtilText
import net.minecraft.client.resources.I18n

class TooltipCache {
    private val tooltipCache = HashMap<String, List<String>>()

    fun getTooltip(tooltipData: TooltipData, tooltipWidth: Int, tooltipHeight: Int): List<String> {
        val key = "${tooltipData.langKey}_${tooltipWidth}_$tooltipHeight"
        val cachedLines = tooltipCache[key]
        if (cachedLines != null)
            return cachedLines

        val tooltip = I18n.format(tooltipData.langKey)
        val lines = UtilText.splitTextToMultiline(tooltip, tooltipWidth, tooltipHeight)
        tooltipCache[key] = lines
        return lines
    }
}