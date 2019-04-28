package aurocosh.divinefavor.client.core.handler.talisman_container;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class MouseHandler {
    @SubscribeEvent
    public static void onMouseEvent(MouseEvent event) {
        if (!KeyBindings.talismanScroll.isKeyDown())
            return;

        int dWheel = event.getDwheel();
        if (dWheel == 0)
            return;

        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        EnumHand hand = UtilPlayer.getHandWithItem(player, TalismanContainerAdapter.INSTANCE::isItemValid);
        if (hand == null)
            return;

        ItemStack stack = player.getHeldItem(hand);
        ITalismanContainer talismanContainer = TalismanContainerAdapter.INSTANCE.getTalismanContainer(stack);
        if (dWheel < 0)
            talismanContainer.switchToPrevious();
        else
            talismanContainer.switchToNext();
        int playerSlot = hand == EnumHand.OFF_HAND ? 40 : player.inventory.currentItem;
        TalismanContainerAdapter.INSTANCE.selectSlot(playerSlot, talismanContainer.getSelectedSlotIndex());
        event.setCanceled(true);
    }
}