package aurocosh.divinefavor.common.network.message.sever;

import aurocosh.divinefavor.common.item.grimoire.capability.GrimoireDataHandler;
import aurocosh.divinefavor.common.item.grimoire.capability.IGrimoireHandler;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class MessageSyncGrimoireSlot extends NetworkWrappedServerMessage {
    public int playerSlotIndex;
    public int selectedGrimoireIndex;

	public MessageSyncGrimoireSlot() {}

    public MessageSyncGrimoireSlot(int playerSlotIndex, int selectedGrimoireIndex) {
	    this.playerSlotIndex = playerSlotIndex;
        this.selectedGrimoireIndex = selectedGrimoireIndex;
    }

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        ItemStack grimoireStack = serverPlayer.inventory.getStackInSlot(playerSlotIndex);
        IGrimoireHandler handler = grimoireStack.getCapability(GrimoireDataHandler.CAPABILITY_GRIMOIRE, null);
        if(handler == null)
            return;
        handler.setSelectedSlotIndex(selectedGrimoireIndex);
    }
}
