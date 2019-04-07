package aurocosh.divinefavor.common.network.message.sever;

import aurocosh.divinefavor.common.item.talisman_container.ITalismanContainer;
import aurocosh.divinefavor.common.item.talisman_container.TalismanContainerAdapter;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class MessageSyncTalismanContainerSlot extends NetworkWrappedServerMessage {
    public int playerSlotIndex;
    public int selectedGrimoireIndex;

	public MessageSyncTalismanContainerSlot() {}

    public MessageSyncTalismanContainerSlot(int playerSlotIndex, int selectedGrimoireIndex) {
	    this.playerSlotIndex = playerSlotIndex;
        this.selectedGrimoireIndex = selectedGrimoireIndex;
    }

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        ItemStack stack = serverPlayer.inventory.getStackInSlot(playerSlotIndex);
        ITalismanContainer talismanContainer = TalismanContainerAdapter.getTalismanContainer(stack);
        if (talismanContainer != null)
            talismanContainer.setSelectedSlotIndex(selectedGrimoireIndex);
    }
}
