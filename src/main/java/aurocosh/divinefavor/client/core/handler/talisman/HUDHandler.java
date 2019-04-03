package aurocosh.divinefavor.client.core.handler.talisman;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public final class HUDHandler {
    @SubscribeEvent
    public void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != ElementType.ALL)
            return;

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(stack.getItem() instanceof ItemTalisman))
            return;

        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        Minecraft mc = Minecraft.getMinecraft();
        TalismanHUD.drawTalismanDescription(mc, width, height, player, stack, false);
    }
}