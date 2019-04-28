package aurocosh.divinefavor.client.core.handler.talisman_container;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.client.core.handler.talisman.TalismanHUD;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
import aurocosh.divinefavor.common.lib.math.Vector2i;
import aurocosh.divinefavor.common.util.UtilGui;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class TalismanSelectGui extends GuiScreen {
    public static final TalismanSelectGui INSTANCE = new TalismanSelectGui();

    private static final ResourceLocation marker = new ResourceLocation(ConstResources.GUI_GRIMOIRE_MARKER);
    private static final ResourceLocation selector = new ResourceLocation(ConstResources.GUI_GRIMOIRE_SELECTOR);

    private EnumHand hand;
    private int state;
    private int selectedIndex;
    private ITalismanContainer handler;

    private List<ItemStack> allStacks;
    private List<Vector2i> slotPositions;
    private Map<Vector2i, Integer> activePositionMap;

    public TalismanSelectGui() {
        mc = Minecraft.getMinecraft();
        hand = null;
        state = 0;
        selectedIndex = 0;
        handler = null;
        allStacks = null;
        slotPositions = null;
        activePositionMap = new HashMap<>();
    }

    @Override
    public void drawScreen(int mx, int my, float partialTicks) {
        super.drawScreen(mx, my, partialTicks);

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        hand = UtilPlayer.getHandWithItem(player, TalismanContainerAdapter.INSTANCE::isItemValid);
        if (hand == null)
            return;

        ITalismanContainer talismanContainer = TalismanContainerAdapter.INSTANCE.getTalismanContainer(player.getHeldItem(hand));

        refreshStackData(talismanContainer);
        renderSpellMassSelector(mx, my, talismanContainer);

        ItemStack talismanStack = talismanContainer.getStackInSlot(selectedIndex);
        TalismanHUD.drawTalismanDescription(mc, width, height, player, talismanStack, true);
    }

    private void renderSpellMassSelector(int mx, int my, ITalismanContainer talismanContainer) {
        int x = width / 2;
        int y = height / 2;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
//        GlStateManager.color(1f, 1f, 1f);

        UtilGui.drawPolyline(slotPositions, 0.3f, 0, 1, 0.3f);

        Vector2i mousePosition = new Vector2i(mx - x, my - y);
        Vector2i closestPoint = UtilGui.findClosestPoint(mousePosition, activePositionMap.keySet(), mousePosition);
        selectedIndex = activePositionMap.get(closestPoint);
        List<Vector2i> mouseLine = Arrays.asList(mousePosition, closestPoint);
        UtilGui.drawPolyline(mouseLine, 0.3f, 0, 1, 0.3f);

        GL11.glColor4f(1f, 1f, 1f, 1f);
        int currentSlotIndex = talismanContainer.getSelectedSlotIndex();
        Vector2i currentPoint = slotPositions.get(currentSlotIndex);

        mc.getTextureManager().bindTexture(marker);
        drawModalRectWithCustomSizedTexture(currentPoint.getX() - 8, currentPoint.getY() - 8, 0, 0, 16, 16, 16, 16);

        mc.getTextureManager().bindTexture(selector);
        drawModalRectWithCustomSizedTexture(closestPoint.getX() - 8, closestPoint.getY() - 8, 0, 0, 16, 16, 16, 16);

        for (Integer index : activePositionMap.values()) {
            Vector2i spiralPoint = slotPositions.get(index);
            ItemStack stack = allStacks.get(index);
            mc.getRenderItem().renderItemIntoGUI(stack, spiralPoint.getX() - 8, spiralPoint.getY() - 8);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

    private void refreshStackData(ITalismanContainer talismanContainer) {
        int k = 3; //scalar
        double a = 0.15f; //a-value
        double aDec = -0.0008; // a-value change over time

        if (slotPositions == null || handler != talismanContainer)
            slotPositions = UtilGui.generateSpiral(talismanContainer.getSlotCount(), 4, k, a, aDec, 9, 7);

        if (handler == talismanContainer && state == talismanContainer.getState())
            return;

        handler = talismanContainer;
        state = talismanContainer.getState();

        allStacks = talismanContainer.getAllStacks();

        List<Integer> activeIndexes = talismanContainer.getSlotIndexes(stack -> !stack.isEmpty());

        activePositionMap.clear();
        for (Integer index : activeIndexes)
            activePositionMap.put(slotPositions.get(index), index);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!GameSettings.isKeyDown(KeyBindings.talismanSelect)) {

            mc.displayGuiScreen(null);
            if (selectedIndex != -1 && hand != null) {
                EntityPlayer player = DivineFavor.proxy.getClientPlayer();
                int playerSlot = hand == EnumHand.OFF_HAND ? 40 : player.inventory.currentItem;
                TalismanContainerAdapter.INSTANCE.selectSlot(playerSlot, selectedIndex);
                selectedIndex = -1;
            }
        }

//        ImmutableSet<KeyBinding> set = ImmutableSet.of(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSneak, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump);
//        for (KeyBinding k : set)
//            KeyBinding.setKeyBindState(k.getKeyCode(), GameSettings.isKeyDown(k));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
