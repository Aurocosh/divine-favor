package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.common.network.message.MessageDataSync;
import aurocosh.divinefavor.common.network.message.MessageSyncPower;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.network.message.MessageSyncTalismanIndexes;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
	public static void init() {
        NetworkHandler.register(MessageDataSync.class, Side.CLIENT);
		NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFavor.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncTalismanIndexes.class, Side.SERVER);
	}
}
