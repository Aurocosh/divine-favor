package aurocosh.divinefavor.common.network.common

import aurocosh.divinefavor.common.network.NetworkHandler
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameActive
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameInactive
import aurocosh.divinefavor.common.network.message.client.config.MessageConfigOreBlocks
import aurocosh.divinefavor.common.network.message.client.particles.*
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncContracts
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavorInfinite
import aurocosh.divinefavor.common.network.message.client.syncing.*
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanContainerSlot
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset
import net.minecraftforge.fml.relauncher.Side

object MessageRegister {
    fun init() {
        // Client messages
        NetworkHandler.register(MessageConfigOreBlocks::class.java, Side.CLIENT)

        NetworkHandler.register(MessageParticlesHighlightEntities::class.java, Side.CLIENT)
        NetworkHandler.register(MessageParticlesHighlightFloodFill::class.java, Side.CLIENT)
        NetworkHandler.register(MessageParticlesHighlightInSphere::class.java, Side.CLIENT)
        NetworkHandler.register(MessageParticlesWave::class.java, Side.CLIENT)
        NetworkHandler.register(MessageParticlesWinterBreath::class.java, Side.CLIENT)

        NetworkHandler.register(MessageSyncAllSpiritData::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncFavorInfinite::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncContracts::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncFavor::class.java, Side.CLIENT)

        NetworkHandler.register(MessagePetrificationReset::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncEvilEye::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncFireImmunity::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncFlyingCapability::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncFury::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncGrudge::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncPotionCharge::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncRedFury::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSyncWindLeash::class.java, Side.CLIENT)

        NetworkHandler.register(MessageSpiritBecameActive::class.java, Side.CLIENT)
        NetworkHandler.register(MessageSpiritBecameInactive::class.java, Side.CLIENT)

        // Server messages
        NetworkHandler.register(MessagePetrificationCure::class.java, Side.SERVER)
        NetworkHandler.register(MessagePetrificationDamage::class.java, Side.SERVER)
        NetworkHandler.register(MessageSyncTalismanContainerSlot::class.java, Side.SERVER)
    }
}
