package aurocosh.divinefavor.client.core.handler;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
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
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (!player.isSneaking())
            return;
        int dWheel = event.getDwheel();
        if (dWheel == 0)
            return;

        EnumHand hand = EnumHand.MAIN_HAND;
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire)) {
            stack = player.getHeldItem(EnumHand.OFF_HAND);
            hand = EnumHand.OFF_HAND;
        }
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemGrimoire))
            return;

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