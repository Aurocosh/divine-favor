package aurocosh.divinefavor.client.gui.interfaces

import aurocosh.divinefavor.common.lib.TooltipData

interface ITooltipProvider{
    val tooltipKey : String
    fun isMouseOver() : Boolean
    fun getTooltipData() : TooltipData
}