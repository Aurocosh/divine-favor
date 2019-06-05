package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeResult {
    var type: String = "minecraft:item"
    var item: String = "minecraft:stick"
    var data: Int = -1
    var count: Int = 1

    fun getItem(): Item? {
        val stackItem = Item.getByNameOrId(item)
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:$item")
            return null
        }
        return stackItem
    }

    fun toItemStack(): ItemStack {
        val itemValue = getItem() ?: return ItemStack.EMPTY
        val ingredientStack = ItemStack(itemValue, count)
        if (data != -1)
            ingredientStack.itemDamage = data
        return ingredientStack
    }
}
