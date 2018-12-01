package aurocosh.divinefavor.client.gui;

import aurocosh.divinefavor.client.gui.buttons.GuiButtonSelectCost;
import aurocosh.divinefavor.client.gui.buttons.GuiButtonSelectCostUnit;
import aurocosh.divinefavor.client.gui.container.GuiCustomScreen;
import aurocosh.divinefavor.client.gui.text.GuiTextBlock;
import aurocosh.divinefavor.common.constants.LibResources;
import aurocosh.divinefavor.common.container.ContainerTalisman;
import aurocosh.divinefavor.common.item.talisman.ItemTalisman;
import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import aurocosh.divinefavor.common.item.talisman.capability.ITalismanCostHandler;
import aurocosh.divinefavor.common.requirements.cost.CostType;
import aurocosh.divinefavor.common.requirements.cost.containers.CostUnit;
import aurocosh.divinefavor.common.requirements.cost.costs.Cost;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiTalismanContainer extends GuiCustomScreen {
    private static final ResourceLocation texture = new ResourceLocation(LibResources.GUI_TALISMAN);
    private static final ResourceLocation costMarkerTexture = new ResourceLocation(LibResources.GUI_COST_MARKER);
    private static final Map<CostType,ResourceLocation> costTextureResources = new HashMap<>();

    private static final int costShiftX = 12;
    private static final int costShiftY = 14;

    private ItemStack talisman;
    private ITalismanCostHandler costHandler;

    public GuiTalismanContainer(EntityPlayer player, ItemStack talisman) {
        super(new ContainerTalisman(player, talisman));
        this.talisman = talisman;
        costTextureResources.put(CostType.FREE,new ResourceLocation(LibResources.GUI_COST_FREE_BUTTON));
        costTextureResources.put(CostType.FAVOR,new ResourceLocation(LibResources.GUI_COST_FAVOR_BUTTON));
        costTextureResources.put(CostType.DAY_TIME,new ResourceLocation(LibResources.GUI_COST_DAY_TIME_BUTTON));
    }

    @Override
    public void initGui() {
        this.xSize = 176;
        this.ySize = 166;
        super.initGui();

        costHandler = TalismanDataHandler.getHandler(talisman);
        if(costHandler == null)
            return;
        String test = "Australian grayling; snake mudhead pricklefish yellowfin pike lumpsucker lake whitefish coelacanth midshipman. Goldeye swampfish deepwater flathead sábalo: shiner northern lampfish jewel tetra. Seahorse bala shark brown trout, \"platyfish slimehead kappy cookie-cutter shark.\" Kelpfish anemonefish parasitic catfish, yellowmargin triggerfish?";

        GuiTextBlock textBlock = new GuiTextBlock(7,25,170,100,test);
        addElement(textBlock);

        ItemTalisman itemTalisman = (ItemTalisman) talisman.getItem();
        List<CostUnit> costUnits = itemTalisman.getRequirement().getCostUnits();

        int nextButtonId = 0;
        for (int i = 0; i < costUnits.size(); i++) {
            CostUnit costUnit = costUnits.get(i);
            GuiButtonSelectCostUnit button = new GuiButtonSelectCostUnit(costHandler, nextButtonId++, guiLeft + 7, guiTop + 81 + costShiftY * i, i,0);
            this.buttonList.add(button);

            List<Cost> costs = costUnit.getCosts();
            for (int j = 0; j < costs.size(); j++) {
                Cost cost = costs.get(j);

                ResourceLocation texture = costTextureResources.get(cost.getType());
                GuiButtonSelectCost buttonCost = new GuiButtonSelectCost(costHandler, nextButtonId++, guiLeft + 21 + costShiftX * j, guiTop + 82 + costShiftY * i, i,j,texture);
                this.buttonList.add(buttonCost);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if(costHandler == null)
            return;

        int unitIndex = costHandler.getSelectedUnitIndex();
        int costIndex = costHandler.getSelectedCostIndex();

        int x = guiLeft + 20 + costShiftX * costIndex;
        int y = guiTop + 81 + costShiftY * unitIndex;

        mc.renderEngine.bindTexture(costMarkerTexture);
        drawModalRectWithCustomSizedTexture (x, y, 0, 0, 10, 10,10,10);
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