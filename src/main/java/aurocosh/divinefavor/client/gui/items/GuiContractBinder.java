package aurocosh.divinefavor.client.gui.items;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.contract_binder.ContractBinderContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiContractBinder extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 129;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_CONTRACT_BINDER);

    ItemStack binder;

    public GuiContractBinder(EntityPlayer player, ItemStack binder) {
        super(new ContractBinderContainer(player, binder.getCapability( CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null )));
        this.binder = binder;
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
