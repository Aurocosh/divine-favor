package aurocosh.divinefavor.common.network.message.sever;

import aurocosh.divinefavor.common.item.spell_bow.capability.ISpellBowHandler;
import aurocosh.divinefavor.common.item.spell_bow.capability.SpellBowDataHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class MessageSyncSpellBowSlot extends NetworkWrappedServerMessage {
    public int playerSlotIndex;
    public int selectedIndex;

	public MessageSyncSpellBowSlot() {}

    public MessageSyncSpellBowSlot(int playerSlotIndex, int selectedIndex) {
	    this.playerSlotIndex = playerSlotIndex;
        this.selectedIndex = selectedIndex;
    }

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        ItemStack grimoireStack = serverPlayer.inventory.getStackInSlot(playerSlotIndex);
        ISpellBowHandler handler = grimoireStack.getCapability(SpellBowDataHandler.CAPABILITY_SPELL_BOW, null);
        if (handler != null)
            handler.setSelectedSlotIndex(selectedIndex);
    }
}
