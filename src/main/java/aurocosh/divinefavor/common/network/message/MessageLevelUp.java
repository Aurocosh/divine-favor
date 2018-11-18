/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [15/02/2016, 18:45:31 (GMT)]
 */
package aurocosh.divinefavor.common.network.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.arl.network.NetworkMessage;

public class MessageLevelUp extends NetworkMessage {

	public int level;

	public MessageLevelUp() { }

	public MessageLevelUp(int level) {
		this.level = level;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		//Psi.proxy.onLevelUp(level);
		return null;
	}

}
