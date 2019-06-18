package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class BookPropertyWrapper(propertyHandler: StackPropertyHandler) {
    private val isBookMode = propertyHandler.registerBoolProperty("is_in_book_mode", false, false)

    @SideOnly(Side.CLIENT)
    fun getValueForModel(stack: ItemStack): Float = (if (stack.get(isBookMode)) 1 else 0).toFloat()

    fun getModeOrTransform(stack: ItemStack, player: EntityPlayer): TalismanContainerMode {
        val isBook = stack.get(isBookMode)
        if (player.isSneaking) {
            stack.set(isBookMode, !isBook)
            return TalismanContainerMode.INVALID
        }
        return if (isBook) TalismanContainerMode.BOOK else TalismanContainerMode.NORMAL
    }
}

