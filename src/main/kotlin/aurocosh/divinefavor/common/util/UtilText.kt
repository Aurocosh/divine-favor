package aurocosh.divinefavor.common.util

import net.minecraft.client.Minecraft

object UtilText {
    fun splitTextToMultiline(text: String, width: Int, height: Int): List<String> {
        val descriptionLines = text.split("\\\\n").dropLastWhile(String::isEmpty)

        val minecraft = Minecraft.getMinecraft()
        val renderer = minecraft.fontRenderer
        val wrappedLines = descriptionLines.flatMap { renderer.listFormattedStringToWidth(it, width) }

        val maxLines = height / 20
        return wrappedLines.take(maxLines)
    }
}
