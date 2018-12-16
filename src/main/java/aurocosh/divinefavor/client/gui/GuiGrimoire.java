package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.grimoire.GrimoireContainer;
import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiGrimoire extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_GRIMOIRE);

    ItemStack grimoire;

    public GuiGrimoire(EntityPlayer player, ItemStack grimoire, EnumHand hand) {
        super(new GrimoireContainer(player, grimoire.getCapability(GrimoireDataHandler.CAPABILITY_GRIMOIRE, null), hand));
        this.grimoire = grimoire;
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
        renderHoveredToolTip(mouseX, mouseY);
    }
}
