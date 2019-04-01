package aurocosh.divinefavor.client.core.handler.grimoire;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.lib.math.Vector2i;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import aurocosh.divinefavor.common.util.UtilGui;
import aurocosh.divinefavor.common.util.UtilPlayer;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;

@Mod.EventBusSubscriber
public class GrimoireSelectGui extends GuiScreen {
    public static GrimoireSelectGui INSTANCE = new GrimoireSelectGui();

    private static final ResourceLocation marker = new ResourceLocation(ConstResources.GUI_GRIMOIRE_MARKER);
    private static final ResourceLocation selector = new ResourceLocation(ConstResources.GUI_GRIMOIRE_SELECTOR);

    private EnumHand hand;
    private int state;
    private int selectedIndex;
    private IGrimoireHandler handler;

    private List<ItemStack> allStacks;
    private List<Vector2i> slotPositions;

    public GrimoireSelectGui() {
        mc = Minecraft.getMinecraft();
        hand = null;
        state = 0;
        selectedIndex = 0;
        handler = null;
        allStacks = null;
        slotPositions = null;
    }

    @Override
    public void drawScreen(int mx, int my, float partialTicks) {
        super.drawScreen(mx, my, partialTicks);

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        hand = UtilPlayer.getHandWithItem(player, item -> item instanceof ItemGrimoire);
        if (hand == null)
            return;

        ItemStack stack = player.getHeldItem(hand);
        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;

        refreshStackData(grimoireHandler);
        renderSpellMassSelector(mx, my, grimoireHandler);
    }

    private void renderSpellMassSelector(int mx, int my, IGrimoireHandler grimoireHandler) {
//
//        ImmutableSet<KeyBinding> set = ImmutableSet.of(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSneak, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump);
//        for (KeyBinding k : set)
//            KeyBinding.setKeyBindState(k.getKeyCode(), GameSettings.isKeyDown(k));

        int x = width / 2;
        int y = height / 2;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
//        GlStateManager.color(1f, 1f, 1f);

        int k = 3; //scalar
        double a = 0.15f; //a-value
        double aDec = -0.0008; // a-value change over time

        if (slotPositions == null || handler != grimoireHandler)
            slotPositions = UtilGui.generateSpiral(allStacks.size(), 4, k, a, aDec, 9, 7);

        UtilGui.drawPolyline(slotPositions, 0.3f, 0, 1, 0.3f);

        Vector2i mousePosition = new Vector2i(mx - x, my - y);
        selectedIndex = UtilGui.findClosestPoint(mousePosition, slotPositions, 0);
        Vector2i closestPoint = slotPositions.get(selectedIndex);
        List<Vector2i> mouseLine = Arrays.asList(mousePosition, closestPoint);
        UtilGui.drawPolyline(mouseLine, 0.3f, 0, 1, 0.3f);

        GL11.glColor4f(1f, 1f, 1f, 1f);
        int currentSlotIndex = grimoireHandler.getSelectedSlotIndex();
        Vector2i currentPoint = slotPositions.get(currentSlotIndex);

        mc.getTextureManager().bindTexture(marker);
        drawModalRectWithCustomSizedTexture(currentPoint.x - 8, currentPoint.y - 8, 0, 0, 16, 16, 16, 16);

        mc.getTextureManager().bindTexture(selector);
        drawModalRectWithCustomSizedTexture(closestPoint.x - 8, closestPoint.y - 8, 0, 0, 16, 16, 16, 16);

        for (int i = 0; i < slotPositions.size(); i++) {
            Vector2i spiralPoint = slotPositions.get(i);
            ItemStack stack = allStacks.get(i);
            mc.getRenderItem().renderItemIntoGUI(stack, spiralPoint.x - 8, spiralPoint.y - 8);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

    private void refreshStackData(IGrimoireHandler grimoireHandler) {
        if (handler != grimoireHandler || state != grimoireHandler.getState()) {
            handler = grimoireHandler;
            state = grimoireHandler.getState();

            allStacks = grimoireHandler.getAllStacks();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!GameSettings.isKeyDown(KeyBindings.talismanSelect)) {

            mc.displayGuiScreen(null);
            if (selectedIndex != -1 && hand != null) {
                EntityPlayer player = DivineFavor.proxy.getClientPlayer();
                int playerSlot = hand == EnumHand.OFF_HAND ? 40 : player.inventory.currentItem;
                new MessageSyncGrimoireSlot(playerSlot, selectedIndex).send();
                selectedIndex = -1;
            }
        }

        ImmutableSet<KeyBinding> set = ImmutableSet.of(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSneak, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump);
        for (KeyBinding k : set)
            KeyBinding.setKeyBindState(k.getKeyCode(), GameSettings.isKeyDown(k));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
