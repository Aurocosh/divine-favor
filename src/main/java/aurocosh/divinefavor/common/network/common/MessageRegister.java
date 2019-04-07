package aurocosh.divinefavor.common.network.common;

import aurocosh.divinefavor.common.network.message.client.*;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHeatWave;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWinterBreath;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncAllFavorData;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncContracts;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSelectedFavors;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
	public static void init() {
        NetworkHandler.register(MessageParticlesHeatWave.class, Side.CLIENT);
        NetworkHandler.register(MessageParticlesWinterBreath.class, Side.CLIENT);

        NetworkHandler.register(MessageSyncAllFavorData.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncContracts.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFavor.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncSelectedFavors.class, Side.CLIENT);

        NetworkHandler.register(MessagePetrificationReset.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncEvilEye.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFireImmunity.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFlyingCapability.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFury.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncGrudge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPotionCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncWindLeash.class, Side.CLIENT);

        NetworkHandler.register(MessagePetrificationCure.class, Side.SERVER);
        NetworkHandler.register(MessagePetrificationDamage.class, Side.SERVER);
        NetworkHandler.register(MessageSyncTalismanContainerSlot.class, Side.SERVER);
    }
}
