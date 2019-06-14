package aurocosh.divinefavor.common.util

import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.input.Mouse

object UtilMouse {
    @SideOnly(Side.CLIENT)
    fun getScrollCount(shiftModifier: Int): Int {
        val i = Mouse.getEventDWheel()
        if (i == 0)
            return 0

        val scroll = if (i > 1) 1 else -1

        if (GuiScreen.isShiftKeyDown())
            return scroll * shiftModifier
        return scroll
    }
}
