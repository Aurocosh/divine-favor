package aurocosh.divinefavor.common.util

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting

object UtilStatus {
    fun formatString(vararg formatting: TextFormatting): String {
        return formatting.joinToString("", transform = TextFormatting::toString)
    }
}
