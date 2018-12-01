package aurocosh.divinefavor.client.gui.container;

import aurocosh.divinefavor.client.gui.IGuiSimpleElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiCustomScreen extends GuiContainer {
    private List<IGuiSimpleElement> elementList;

    public GuiCustomScreen(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.elementList = new ArrayList<>();
    }

    protected <T extends IGuiSimpleElement> T addElement(T element) {
        this.elementList.add(element);
        return element;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (IGuiSimpleElement element: elementList)
            element.draw(this.mc,guiLeft,guiTop);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        elementList.clear();
        super.setWorldAndResolution(mc, width, height);
    }
}
