package aurocosh.divinefavor.client.gui

import net.minecraft.client.Minecraft

interface IGuiSimpleElement {
    fun draw(minecraft: Minecraft, guiLeft: Int, guiTop: Int)
}
