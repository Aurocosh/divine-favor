/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 *
 * File Created @ [12/01/2016, 16:45:17 (GMT)]
 */
package aurocosh.divinefavor.common.network.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.arl.network.NetworkMessage;

public class MessageDeductPsi extends NetworkMessage {

	public int prev;
	public int current;
	public int cd;
	public boolean shatter;

	public MessageDeductPsi() { }

	public MessageDeductPsi(int prev, int current, int cd, boolean shatter) {
		this.prev = prev;
		this.current = current;
		this.cd = cd;
		this.shatter = shatter;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
		/*ClientTickHandler.scheduledActions.add(() -> {
			PlayerData data = PlayerDataHandler.get(Psi.proxy.getClientPlayer());
			data.lastAvailablePsi = data.availablePsi;
			data.availablePsi = current;
			data.regenCooldown = cd;
			data.deductTick = true;
			data.addDeduction(prev, prev - current, shatter);
		});*/

		return null;
	}

}
