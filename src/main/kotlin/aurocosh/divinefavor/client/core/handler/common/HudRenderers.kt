package aurocosh.divinefavor.client.core.handler.common

import aurocosh.divinefavor.client.core.handler.base.IHudDescriptionRenderer
import aurocosh.divinefavor.client.core.handler.stable_gem.WarpMarkerHudDescriptionRenderer
import aurocosh.divinefavor.client.core.handler.talisman.TalismanHudDescriptionRenderer
import aurocosh.divinefavor.common.item.gems.base.IStackUsableGemItem
import aurocosh.divinefavor.common.item.talisman.ItemTalisman
import net.minecraft.item.Item

object HudRenderers {
    fun getRenderer(item: Item): IHudDescriptionRenderer? {
        return when (item) {
            is ItemTalisman -> TalismanHudDescriptionRenderer
            is IStackUsableGemItem -> WarpMarkerHudDescriptionRenderer
            else -> null
        }
    }
}
