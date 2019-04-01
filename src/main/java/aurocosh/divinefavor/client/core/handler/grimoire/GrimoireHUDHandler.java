package aurocosh.divinefavor.client.core.handler.grimoire;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.hud.UtilHUD;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.lib.math.Vector2i;
import aurocosh.divinefavor.common.util.UtilGui;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;

@Mod.EventBusSubscriber
public class GrimoireHUDHandler {
    private static IGrimoireHandler handler = null;
    private static int stateSelector = 0;
    private static int stateMassSelector = 0;

    private static ItemStack selectedStack = ItemStack.EMPTY;
    private static List<ItemStack> nextStacks = null;
    private static List<ItemStack> previousStacks = null;

    private static List<ItemStack> allStacks = null;




    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = event.getResolution();
            float partialTicks = event.getPartialTicks();

            Minecraft mc = Minecraft.getMinecraft();
            ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
            if (stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire))
                stack = mc.player.getHeldItem(EnumHand.OFF_HAND);
            if (stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire))
                return;
            IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
            assert grimoireHandler != null;

            EntityPlayer player = DivineFavor.proxy.getClientPlayer();

            renderSpellSelector(mc, resolution, partialTicks, player, grimoireHandler);
//            renderSpellMassSelector(mc, resolution, partialTicks, player, grimoireHandler);
            renderSpellRequirements(mc, resolution, partialTicks, player, grimoireHandler);
        }
    }



    @SideOnly(Side.CLIENT)
    private static void renderSpellSelector(Minecraft mc, ScaledResolution res, float pticks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
        if (!player.isSneaking())
            return;
        if (handler != grimoireHandler || stateSelector != grimoireHandler.getState()) {
            handler = grimoireHandler;
            stateSelector = grimoireHandler.getState();

            selectedStack = grimoireHandler.getSelectedStack();
            previousStacks = new ArrayList<>(grimoireHandler.getPreviousStacks());
            nextStacks = grimoireHandler.getNextStacks();

            for (ItemStack stack : nextStacks)
                previousStacks.remove(stack);
        }
        if (selectedStack.isEmpty())
            return;

        int alpha = 255;
        int x = res.getScaledWidth() / 2;
        int y = 10;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);
        mc.getRenderItem().renderItemIntoGUI(selectedStack, 0, 0);
        for (int i = 0; i < nextStacks.size(); i++)
            mc.getRenderItem().renderItemIntoGUI(nextStacks.get(i), i * 18 + 21, -6);
        for (int i = 0; i < previousStacks.size(); i++)
            mc.getRenderItem().renderItemIntoGUI(previousStacks.get(i), -i * 18 - 21, -6);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }



    @SideOnly(Side.CLIENT)
    private static void renderSpellMassSelector(Minecraft mc, ScaledResolution res, float pticks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
//        if (!player.isSneaking())
//            return;
        if (handler != grimoireHandler || stateMassSelector != grimoireHandler.getState()) {
            handler = grimoireHandler;
            stateMassSelector = grimoireHandler.getState();

            selectedStack = grimoireHandler.getSelectedStack();
            allStacks = grimoireHandler.getAllStacks();
        }
//        if (selectedStack.isEmpty())
//            return;

        mc.displayGuiScreen(null);

        ImmutableSet<KeyBinding> set = ImmutableSet.of(mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindSneak, mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump);
        for (KeyBinding k : set)
            KeyBinding.setKeyBindState(k.getKeyCode(), GameSettings.isKeyDown(k));


        int alpha = 255;
        int x = res.getScaledWidth() / 2;
        int y = res.getScaledHeight() / 2;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);

        int numSegments = allStacks.size();

        int k = 3; //scalar
        double a = 0.15f; //a-value
        double aDec = 0.0008;

        List<Vector2i> spiralPoints = new ArrayList<>();

        for (int i = 0; i < numSegments; ++i) {

            //find `ln(d*r)/a` for both points
            double lni1 = Math.log(a * k * (i + 4)) / a;
            //Calculate the points and convert to rectangular coordinates, zooming in with 7x magnification
            double logSpiral = Math.exp(lni1 * a);
            double pointX = 9 * logSpiral * Math.cos(lni1);
            double pointY = 7 * logSpiral * Math.sin(lni1);

            spiralPoints.add(new Vector2i((int)pointX, (int)pointY));

            a -= aDec;

        }

        UtilGui.drawPolyline(spiralPoints, 0.3f, 0, 1, 0.3f);

        for (int i = 0; i < spiralPoints.size(); i++) {
            Vector2i spiralPoint = spiralPoints.get(i);
            ItemStack stack = allStacks.get(i);
            mc.getRenderItem().renderItemIntoGUI(stack, spiralPoint.x - 8, spiralPoint.y - 8);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }

    @SideOnly(Side.CLIENT)
    private static void renderSpellRequirements(Minecraft mc, ScaledResolution res, float partialTicks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        ItemStack talismanStack = grimoireHandler.getSelectedStack();
        if (!talismanStack.isEmpty())
            UtilHUD.drawTalismanDescription(mc, res, partialTicks, player, talismanStack);
    }
}
