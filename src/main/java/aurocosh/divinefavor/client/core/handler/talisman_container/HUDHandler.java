package aurocosh.divinefavor.client.core.handler.talisman_container;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.client.core.handler.talisman.TalismanHUD;
import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
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

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class HUDHandler {
    private static TalismanScrollHUD scrollHUD = new TalismanScrollHUD();

    @SubscribeEvent
    public static void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ItemStack stack = UtilPlayer.getItemInHand(player, TalismanContainerAdapter::isItemValid);
        if (stack.isEmpty())
            return;

        ITalismanContainer talismanContainer = TalismanContainerAdapter.getTalismanContainer(stack);
        assert talismanContainer != null;

        Minecraft mc = Minecraft.getMinecraft();

        if (KeyBindings.talismanScroll.isKeyDown())
            scrollHUD.draw(mc, width, height, talismanContainer);

        if (mc.currentScreen != TalismanSelectGui.INSTANCE)
            TalismanHUD.drawTalismanDescription(mc, width, height, player, talismanContainer.getSelectedStack(), true);
    }
}
