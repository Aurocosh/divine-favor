package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.client.gui.container.GuiCustomScreen;
import aurocosh.divinefavor.client.gui.text.GuiTextBlock;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.talisman.ContainerTalisman;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import mezz.jei.util.Translator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public class GuiTalismanContainer extends GuiCustomScreen {
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_TALISMAN);

    private ItemStack talisman;

    public GuiTalismanContainer(EntityPlayer player, ItemStack talisman) {
        super(new ContainerTalisman(player, talisman));
        this.talisman = talisman;
    }

    @Override
    public void initGui() {
        this.xSize = 176;
        this.ySize = 166;
        super.initGui();

        ItemTalisman itemTalisman = (ItemTalisman) talisman.getItem();
        String text = Translator.translateToLocal(itemTalisman.getCostTranslationKey());
        GuiTextBlock textBlock = new GuiTextBlock(5,7,170,250,text);
        addElement(textBlock);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton B)
    {
        IActionButton button = (IActionButton)B;
        if(button == null)
            return;
        button.performAction();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}