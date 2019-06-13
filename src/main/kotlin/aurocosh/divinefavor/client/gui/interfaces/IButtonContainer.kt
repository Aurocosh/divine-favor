package aurocosh.divinefavor.client.gui.interfaces

import net.minecraft.client.gui.GuiButton
import java.awt.Rectangle

interface IButtonContainer {
    val rect: Rectangle
    val components: List<GuiButton>
}