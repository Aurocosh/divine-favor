package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeResult {
    var type: String = "minecraft:item"
    var item: String = "minecraft:stick"
    var data: Int = -1
    var count: Int = 1

    fun toItemStack(): ItemStack {
        val stackItem = Item.getByNameOrId(item)
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:$item")
            return ItemStack.EMPTY
        }

        return ItemStack(stackItem, count, data)
    }
}
