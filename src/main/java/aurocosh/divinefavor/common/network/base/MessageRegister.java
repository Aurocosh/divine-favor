package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.common.network.message.*;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
	public static void init() {
        NetworkHandler.register(MessageDataSync.class, Side.CLIENT);
		NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncSpellCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncTalismanIndexes.class, Side.SERVER);
	}
}
