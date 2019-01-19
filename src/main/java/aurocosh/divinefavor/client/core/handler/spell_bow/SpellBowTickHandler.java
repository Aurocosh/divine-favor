package aurocosh.divinefavor.client.core.handler.spell_bow;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.item.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncSpellBowSlot;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import static aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;
import static aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW;


@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
public class SpellBowTickHandler {
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

        EnumHand hand = UtilPlayer.getHand(element -> player.getHeldItem(element).getItem() instanceof ItemSpellBow);
        if(hand == null)
            return;
        ItemStack stack = player.getHeldItem(hand);
        ISpellBowHandler handler = stack.getCapability(CAPABILITY_SPELL_BOW, null);
        assert handler != null;
        if (dWheel < 0)
            handler.switchToPrevious();
        else
            handler.switchToNext();
        int playerSlot = hand == EnumHand.OFF_HAND ? InventoryIndexes.Offhand.getValue() : player.inventory.currentItem;
        new MessageSyncSpellBowSlot(playerSlot, handler.getSelectedSlotIndex()).send();
        event.setCanceled(true);
    }
}