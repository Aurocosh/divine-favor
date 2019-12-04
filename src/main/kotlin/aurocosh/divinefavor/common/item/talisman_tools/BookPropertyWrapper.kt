package aurocosh.divinefavor.common.item.talisman_tools

import aurocosh.divinefavor.common.constants.ConstLang
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.stack_properties.StackPropertyHandler
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBool
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class BookPropertyWrapper(propertyHandler: StackPropertyHandler) {
    init {
        propertyHandler.registerProperty(isToolSealed)
        propertyHandler.registerProperty(isBookMode)
    }

    @SideOnly(Side.CLIENT)
    fun getValueForModel(stack: ItemStack): Float = (if (stack.get(isBookMode)) 1 else 0).toFloat()

    fun getModeOrTransform(stack: ItemStack, player: EntityPlayer): TalismanContainerMode {
        val (isBook, isSealed) = stack.get(isBookMode, isToolSealed)
        if (isSealed || !player.isSneaking)
            return if (isBook) TalismanContainerMode.BOOK else TalismanContainerMode.NORMAL
        stack.set(isBookMode, !isBook)
        return TalismanContainerMode.INVALID
    }

    companion object {
        val isToolSealed = StackPropertyBool("is_tool_sealed", defaultValue = false, showInTooltip = true, showInGui = false, orderIndex = 0, onKey = ConstLang.yesKey, offKey = ConstLang.noKey)
        val isBookMode = StackPropertyBool("is_in_book_mode", defaultValue = false, showInTooltip = false, showInGui = false, orderIndex = 0)
    }
}

