package aurocosh.divinefavor.client.core.handler.common

import aurocosh.divinefavor.client.core.handler.base.IDisplayStackExtractor
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.lib.extensions.get
import com.google.common.cache.CacheBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object WarpMarkerDisplayStackExtractor : IDisplayStackExtractor {
    private val stackCache = CacheBuilder
            .newBuilder()
            .maximumSize(64)
            .build<String, ItemStack>();

    override fun getDisplayStack(stack: ItemStack): ItemStack {
        val itemId = stack.get(GemMaskProperties.maskItemId)
        val itemMeta = stack.get(GemMaskProperties.maskItemMeta)

        val item = Item.getByNameOrId(itemId) ?: return stack
        val key = itemId + itemMeta;

        return stackCache.get(key) { ItemStack(item,1,itemMeta) }
    }
}