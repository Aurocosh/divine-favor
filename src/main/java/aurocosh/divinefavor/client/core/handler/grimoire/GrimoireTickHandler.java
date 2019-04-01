package aurocosh.divinefavor.client.core.handler.grimoire;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.KeyBindings;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;


@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class GrimoireTickHandler {
    @SubscribeEvent
    public static void onMouseEvent(MouseEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
            return;
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if (!KeyBindings.talismanScroll.isKeyDown())
            return;
        int dWheel = event.getDwheel();
        if (dWheel == 0)
            return;

        EnumHand hand = UtilPlayer.getHandWithItem(player, element -> element instanceof ItemGrimoire);
        if(hand == null)
            return;

        ItemStack stack = player.getHeldItem(hand);
        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        assert grimoireHandler != null;
        if (dWheel < 0)
            grimoireHandler.switchToPrevious();
        else
            grimoireHandler.switchToNext();
        int playerSlot = hand == EnumHand.OFF_HAND ? 40 : player.inventory.currentItem;
        new MessageSyncGrimoireSlot(playerSlot, grimoireHandler.getSelectedSlotIndex()).send();
        event.setCanceled(true);
    }
}