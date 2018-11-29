/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [11/01/2016, 21:58:25 (GMT)]
 */
package aurocosh.divinefavor.common.network;

import aurocosh.divinefavor.common.network.message.*;
import net.minecraftforge.fml.relauncher.Side;
import vazkii.arl.network.NetworkHandler;

public class MessageRegister {

	public static void init() {
        NetworkHandler.register(MessageDataSync.class, Side.CLIENT);
		NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncSpellCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncTalismanUnitIndex.class, Side.SERVER);
	}
}
