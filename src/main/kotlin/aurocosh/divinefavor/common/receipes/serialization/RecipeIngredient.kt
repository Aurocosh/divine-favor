package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeIngredient {
    var item: String
    var data: Int = 0
    var count: Int = 0

    init {
        this.item = "minecraft:stick"
        this.data = -1
        this.count = 1
    }

    fun getItem(): Item? {
        val stackItem = Item.getByNameOrId(item)
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:$item")
            return null
        }
        return stackItem
    }

    fun toItemStack(): ItemStack {
        val ingredientStack = ItemStack(getItem()!!, count)
        if (data != -1)
            ingredientStack.itemDamage = data
        return ingredientStack
    }
}
