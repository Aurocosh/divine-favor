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

import aurocosh.divinefavor.common.network.message.MessageDeductPsi;
import aurocosh.divinefavor.common.network.message.MessageLevelUp;
import aurocosh.divinefavor.common.network.message.SyncPowerMessage;
import net.minecraftforge.fml.relauncher.Side;
import vazkii.arl.network.NetworkHandler;

public class MessageRegister {

	public static void init() {
		NetworkHandler.register(MessageDeductPsi.class, Side.CLIENT);
		NetworkHandler.register(MessageLevelUp.class, Side.CLIENT);
		NetworkHandler.register(SyncPowerMessage.class, Side.CLIENT);
	}
}
