package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.common.capabilities.Capability

data class TalismanStackWrapper<T : ItemTalisman>(val stack: ItemStack, val talisman: T)

open class ItemTalismanContainer(name: String, texturePath: String, orderIndex: Int = 0) : ModItem(name, texturePath, orderIndex) {
    init {
        setMaxStackSize(1)
    }

    protected fun getModeOrTransform(stack: ItemStack, player: EntityPlayer): TalismanContainerMode {
        val compound = stack.compound
        val isBook = compound.getBoolean(TAG_IS_IN_BOOK_MODE)
        if (player.isSneaking) {
            compound.setBoolean(TAG_IS_IN_BOOK_MODE, !isBook)
            return TalismanContainerMode.INVALID
        }
        return if (isBook) TalismanContainerMode.BOOK else TalismanContainerMode.NORMAL
    }

    protected inline fun <reified T : ItemTalisman> getTalisman(stack: ItemStack): TalismanStackWrapper<T>? {
        val talismanContainer = TalismanContainerAdapter.getTalismanContainer(stack) ?: return null
        val talismanStack = talismanContainer.getSelectedStack()
        if (talismanStack.isEmpty)
            return null

        val talisman = talismanStack.item as? T ?: return null
        return TalismanStackWrapper(talismanStack, talisman)
    }

    override fun getShareTag(): Boolean {
        return true
    }

    companion object {
        const val SLOT_COUNT = 18
        const val TAG_IS_IN_BOOK_MODE = "IsInBookMode"
    }
}

