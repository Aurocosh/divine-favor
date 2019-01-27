package aurocosh.divinefavor.client.core.handler.spell_bow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.hud.UtilHUD;
import aurocosh.divinefavor.common.item.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.item.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
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

import static aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW;

@Mod.EventBusSubscriber
public class SpellBowHUDHandler {
    private static ISpellBowHandler handler = null;
    private static int state = 0;

    private static ItemStack selectedStack = ItemStack.EMPTY;
    private static List<ItemStack> nextStacks = null;
    private static List<ItemStack> previousStacks = null;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = event.getResolution();
            float partialTicks = event.getPartialTicks();

            EntityPlayer player = DivineFavor.proxy.getClientPlayer();
            EnumHand hand = UtilPlayer.getHand(element -> player.getHeldItem(element).getItem() instanceof ItemSpellBow);
            if (hand == null)
                return;

            ItemStack stack = player.getHeldItem(hand);
            ISpellBowHandler bowHandler = stack.getCapability(CAPABILITY_SPELL_BOW, null);
            assert bowHandler != null;

            Minecraft mc = Minecraft.getMinecraft();
            renderSpellSelector(mc, resolution, partialTicks, player, bowHandler);
            renderSpellRequirements(mc, resolution, partialTicks, player, bowHandler);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void renderSpellSelector(Minecraft mc, ScaledResolution res, float pticks, EntityPlayer player, ISpellBowHandler bowHandler) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
        if (!player.isSneaking())
            return;
        if (handler != bowHandler || state != bowHandler.getState()) {
            handler = bowHandler;
            state = bowHandler.getState();

            selectedStack = bowHandler.getSelectedStack();
            previousStacks = new ArrayList<>(bowHandler.getPreviousStacks());
            nextStacks = bowHandler.getNextStacks();

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
    private static void renderSpellRequirements(Minecraft mc, ScaledResolution res, float partialTicks, EntityPlayer player, ISpellBowHandler bowHandler) {
        ItemStack talismanStack = bowHandler.getSelectedStack();
        if (!talismanStack.isEmpty())
            UtilHUD.drawTalismanDescription(mc, res, partialTicks, player, talismanStack);
    }
}
