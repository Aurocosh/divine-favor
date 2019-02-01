package aurocosh.divinefavor.client.gui.blocks;

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLectern;
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.contracts.ContractsData;
import aurocosh.divinefavor.common.item.ItemBloodCrystal;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.UUID;

public class GuiSoulboundLectern extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation textureBound = new ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_BOUND);
    private static final ResourceLocation textureUnbound = new ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_UNBOUND);
    private boolean isBound;

    TileSoulboundLectern lectern;

    public GuiSoulboundLectern(EntityPlayer player, TileSoulboundLectern lectern) {
        super(new ContainerSoulboundLectern(player, lectern));
        this.lectern = lectern;

        IItemHandler crystalHandler = lectern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        assert crystalHandler != null;
        ItemStack stack = crystalHandler.getStackInSlot(0);
        UUID playerUUID = player.getGameProfile().getId();
        UUID stackUUID = ItemBloodCrystal.getPlayerId(stack);
        isBound = playerUUID.equals(stackUUID);
    }

    @Override
    public void initGui() {
        xSize = WIDTH;
        ySize = HEIGHT;
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(isBound ? textureBound : textureUnbound);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX,mouseY);
    }
}
