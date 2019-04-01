package aurocosh.divinefavor.client.core.handler.grimoire;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.hud.UtilHUD;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;

@Mod.EventBusSubscriber
public class GrimoireHUDHandler {
    private static GrimoireScrollHUD scrollHUD = new GrimoireScrollHUD();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = event.getResolution();
            float partialTicks = event.getPartialTicks();

            EntityPlayer player = DivineFavor.proxy.getClientPlayer();
            ItemStack stack = UtilPlayer.getItemInHand(player, item -> item instanceof ItemGrimoire);
            if(stack.isEmpty())
                return;

            IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
            assert grimoireHandler != null;

            Minecraft mc = Minecraft.getMinecraft();
            scrollHUD.draw(mc,resolution,grimoireHandler);
            renderSpellRequirements(mc, resolution, partialTicks, player, grimoireHandler);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void renderSpellRequirements(Minecraft mc, ScaledResolution res, float partialTicks, EntityPlayer player, IGrimoireHandler grimoireHandler) {
        ItemStack talismanStack = grimoireHandler.getSelectedStack();
        if (!talismanStack.isEmpty())
            UtilHUD.drawTalismanDescription(mc, res, partialTicks, player, talismanStack);
    }
}
