package aurocosh.divinefavor.client.gui.items;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.ritual_pouch.RitualBagContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiRitualPouch extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_RITUAL_POUCH);

    ItemStack pouch;

    public GuiRitualPouch(EntityPlayer player, ItemStack pouch) {
        super(new RitualBagContainer(player, pouch.getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null )));
        this.pouch = pouch;
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX,mouseY);
    }
}
