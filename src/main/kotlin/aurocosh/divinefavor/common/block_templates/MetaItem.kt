package aurocosh.divinefavor.common.block_templates

import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

data class MetaItem(val item: Item = Items.AIR, val meta: Int = 0) {
    fun toStack(count: Int = 1): ItemStack {
        return ItemStack(item, count, meta)
    }
}
