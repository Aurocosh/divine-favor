package aurocosh.divinefavor.client.gui.blocks.soulbound_lectern;

import aurocosh.divinefavor.common.block.soulbound_lectern.ContainerSoulboundLecternWithShard;
import aurocosh.divinefavor.common.block.soulbound_lectern.TileSoulboundLectern;
import aurocosh.divinefavor.common.constants.ConstResources;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiSoulboundLecternWithShard extends GuiContainer {
    private static final int WIDTH = 175;
    private static final int HEIGHT = 165;
    private static final ResourceLocation textureBound = new ResourceLocation(ConstResources.GUI_SOULBOUND_LECTERN_WITH_SHARD);

    private final ItemStack shardStack;

    public GuiSoulboundLecternWithShard(EntityPlayer player, TileSoulboundLectern lectern) {
        super(new ContainerSoulboundLecternWithShard(player, lectern));
        shardStack = lectern.getShardStack();
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
        mc.getRenderItem().renderItemIntoGUI(shardStack, guiLeft + 80, guiTop + 36);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
