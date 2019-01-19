package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
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

import java.util.List;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;

@Mod.EventBusSubscriber
public class GrimoireHUDHandler {
    private static IGrimoireHandler handler = null;
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

            Minecraft mc = Minecraft.getMinecraft();
            ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
            if(stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire))
                stack = mc.player.getHeldItem(EnumHand.OFF_HAND);
            if(stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire))
                return;
            IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
            assert grimoireHandler != null;

            EntityPlayer player = DivineFavor.proxy.getClientPlayer();

            renderSpellSelector(mc, resolution, partialTicks, player, grimoireHandler);
            renderSpellRequirements(mc, resolution, partialTicks, player, grimoireHandler);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void renderSpellSelector(Minecraft mc, ScaledResolution res, float pticks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
        if(!player.isSneaking())
            return;
        if(handler != grimoireHandler || state != grimoireHandler.getState()){
            handler = grimoireHandler;
            state = grimoireHandler.getState();

            selectedStack = grimoireHandler.getSelectedStack();
            previousStacks = grimoireHandler.getPreviousStacks();
            nextStacks = grimoireHandler.getNextStacks();
        }
        if(selectedStack.isEmpty())
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
    private static void renderSpellRequirements(Minecraft mc, ScaledResolution res, float pticks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        ItemStack talismanStack = grimoireHandler.getSelectedStack();
        if(talismanStack.isEmpty())
            return;
        ItemTalisman talisman = (ItemTalisman) talismanStack.getItem();
        int useCount = talisman.getUseCount(player);
        String description = useCount > 0 ? "Uses left: " + useCount : "Unusable";
        int alpha = 255;

        int color = (0 << 0) + (128 << 8) + (0 << 16) + (alpha << 24);

        int x = res.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(description) / 2;
        int y = res.getScaledHeight() - 71;
        if (mc.player.capabilities.isCreativeMode)
            y += 14;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.fontRenderer.drawStringWithShadow(description, x, y, color);

        int w = mc.fontRenderer.getStringWidth(description);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - 20, y - 6, 0);
        GlStateManager.scale(alpha / 255F, 1F, 1);
        GlStateManager.color(1F, 1F, 1F);
        mc.getRenderItem().renderItemIntoGUI(talismanStack, 0, 0);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }
}
