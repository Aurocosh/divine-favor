package aurocosh.divinefavor.common.network.common;

import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameActive;
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameInactive;
import aurocosh.divinefavor.common.network.message.client.config.MessageConfigOreBlocks;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHeatWave;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightFloodFill;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHighlightInSphere;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWinterBreath;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncContracts;
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor;
import aurocosh.divinefavor.common.network.message.client.syncing.*;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
    public static void init() {
        // Client messages
        NetworkHandler.register(MessageConfigOreBlocks.class, Side.CLIENT);

        NetworkHandler.register(MessageParticlesHeatWave.class, Side.CLIENT);
        NetworkHandler.register(MessageParticlesHighlightFloodFill.class, Side.CLIENT);
        NetworkHandler.register(MessageParticlesHighlightInSphere.class, Side.CLIENT);
        NetworkHandler.register(MessageParticlesWinterBreath.class, Side.CLIENT);

        NetworkHandler.register(MessageSyncAllSpiritData.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncContracts.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFavor.class, Side.CLIENT);

        NetworkHandler.register(MessagePetrificationReset.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncEvilEye.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFireImmunity.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFlyingCapability.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFury.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncGrudge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPotionCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncRedFury.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncWindLeash.class, Side.CLIENT);

        NetworkHandler.register(MessageSpiritBecameActive.class, Side.CLIENT);
        NetworkHandler.register(MessageSpiritBecameInactive.class, Side.CLIENT);

        // Server messages
        NetworkHandler.register(MessagePetrificationCure.class, Side.SERVER);
        NetworkHandler.register(MessagePetrificationDamage.class, Side.SERVER);
        NetworkHandler.register(MessageSyncTalismanContainerSlot.class, Side.SERVER);
    }
}
