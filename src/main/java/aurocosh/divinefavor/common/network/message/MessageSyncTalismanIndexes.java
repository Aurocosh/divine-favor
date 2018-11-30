package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.item.talisman.capability.TalismanDataHandler;
import aurocosh.divinefavor.common.item.talisman.capability.ITalismanCostHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.arl.network.NetworkMessage;


public class MessageSyncTalismanIndexes extends NetworkMessage {
    public int unitIndex;
    public int costIndex;

	public MessageSyncTalismanIndexes() { }

    public MessageSyncTalismanIndexes(int unitIndex, int costIndex) {
        this.unitIndex = unitIndex;
        this.costIndex = costIndex;
    }

    @Override
	public IMessage handleMessage(MessageContext context) {
        ClientTickHandler.scheduledActions.add(() -> {
            ItemStack heldItem = DivineFavor.proxy.getClientPlayer().getHeldItem(EnumHand.MAIN_HAND);
            ITalismanCostHandler handler = TalismanDataHandler.getHandler(heldItem);
            if(handler == null)
                return;
            handler.setUnitIndex(unitIndex);
            handler.setCostIndex(costIndex);
        });
        return null;
    }
}
