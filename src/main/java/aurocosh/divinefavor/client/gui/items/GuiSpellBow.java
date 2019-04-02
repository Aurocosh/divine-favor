package aurocosh.divinefavor.client.gui.items;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.SpellBowContainer;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class GuiSpellBow extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;
    private static final ResourceLocation texture = new ResourceLocation(ConstResources.GUI_GRIMOIRE);

    ItemStack spellBow;

    public GuiSpellBow(EntityPlayer player, ItemStack spellBow, EnumHand hand) {
        super(new SpellBowContainer(player, spellBow.getCapability(SpellBowDataHandler.CAPABILITY_SPELL_BOW, null), hand));
        this.spellBow = spellBow;
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
