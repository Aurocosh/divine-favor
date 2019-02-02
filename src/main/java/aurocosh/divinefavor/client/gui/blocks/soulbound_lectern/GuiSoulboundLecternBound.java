package aurocosh.divinefavor.client.gui.blocks.soulbound_lectern;

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternBound;
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.custom_data.CapabilityHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

public class GuiSoulboundLecternBound extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation textureBound = new ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_BOUND);

    private static final int crystalX = 80;
    private static final int crystalY = 36;

    private final ItemStack crystalStack;

    TileSoulboundLectern lectern;

    public GuiSoulboundLecternBound(EntityPlayer player, TileSoulboundLectern lectern) {
        super(new ContainerSoulboundLecternBound(player, lectern));
        this.lectern = lectern;
        IItemHandler handler = CapabilityHelper.getItemHandler(lectern);
        crystalStack = handler.getStackInSlot(0);
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(textureBound);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        mc.getRenderItem().renderItemIntoGUI(crystalStack, crystalX, crystalY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
