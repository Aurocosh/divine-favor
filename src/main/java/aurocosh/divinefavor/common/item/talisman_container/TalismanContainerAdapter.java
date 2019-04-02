package aurocosh.divinefavor.common.item.talisman_container;

import aurocosh.divinefavor.common.item.talisman_container.grimoire.ItemGrimoire;
import aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static aurocosh.divinefavor.common.item.talisman_container.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE;
import static aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability.SpellBowDataHandler.CAPABILITY_SPELL_BOW;

public class TalismanContainerAdapter {
    public static boolean isItemValid(Item item) {
        return item instanceof ItemGrimoire || item instanceof ItemSpellBow;
    }

    public static ITalismanContainer getTalismanContainer(ItemStack stack) {
        IGrimoireHandler grimoireHandler = stack.getCapability(CAPABILITY_GRIMOIRE, null);
        if(grimoireHandler != null)
            return grimoireHandler;
        return stack.getCapability(CAPABILITY_SPELL_BOW, null);
    }

    public static void selectSlot(int playerSlot, int talismanSlot) {
        new MessageSyncTalismanContainerSlot(playerSlot, talismanSlot).send();
    }
}
