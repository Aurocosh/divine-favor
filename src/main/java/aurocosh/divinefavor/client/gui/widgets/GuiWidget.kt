package aurocosh.divinefavor.client.gui.widgets

import aurocosh.divinefavor.client.gui.IGuiSimpleElement
import net.minecraft.client.gui.Gui

abstract class GuiWidget(protected var x: Int, protected var y: Int, protected var width: Int, protected var height: Int) : Gui(), IGuiSimpleElement
