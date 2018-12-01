package aurocosh.divinefavor.client.gui.widgets;

import aurocosh.divinefavor.client.gui.IGuiSimpleElement;
import net.minecraft.client.gui.Gui;

public abstract class GuiWidget extends Gui implements IGuiSimpleElement {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GuiWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
