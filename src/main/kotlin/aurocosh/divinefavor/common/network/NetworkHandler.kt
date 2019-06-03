package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.network.AutoNetworkWrapper
import aurocosh.divinefavor.common.constants.ConstMisc
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

object NetworkHandler {
    val wrapper = AutoNetworkWrapper(ConstMisc.MOD_ID)

    fun init() {
        // Client messages
        wrapper.register(MessageConfigOreBlocks::class.java, Side.CLIENT)

        wrapper.register(MessageParticlesHighlightEntities::class.java, Side.CLIENT)
        wrapper.register(MessageParticlesHighlightFloodFill::class.java, Side.CLIENT)
        wrapper.register(MessageParticlesHighlightInSphere::class.java, Side.CLIENT)
        wrapper.register(MessageParticlesWave::class.java, Side.CLIENT)
        wrapper.register(MessageParticlesWinterBreath::class.java, Side.CLIENT)

        wrapper.register(MessageSyncAllSpiritData::class.java, Side.CLIENT)
        wrapper.register(MessageSyncFavorInfinite::class.java, Side.CLIENT)
        wrapper.register(MessageSyncContracts::class.java, Side.CLIENT)
        wrapper.register(MessageSyncFavor::class.java, Side.CLIENT)

        wrapper.register(MessagePetrificationReset::class.java, Side.CLIENT)
        wrapper.register(MessageSyncEvilEye::class.java, Side.CLIENT)
        wrapper.register(MessageSyncFireImmunity::class.java, Side.CLIENT)
        wrapper.register(MessageSyncFlyingCapability::class.java, Side.CLIENT)
        wrapper.register(MessageSyncFury::class.java, Side.CLIENT)
        wrapper.register(MessageSyncGrudge::class.java, Side.CLIENT)
        wrapper.register(MessageSyncPotionCharge::class.java, Side.CLIENT)
        wrapper.register(MessageSyncRedFury::class.java, Side.CLIENT)
        wrapper.register(MessageSyncWindLeash::class.java, Side.CLIENT)

        wrapper.register(MessageSpiritBecameActive::class.java, Side.CLIENT)
        wrapper.register(MessageSpiritBecameInactive::class.java, Side.CLIENT)

        // Server messages
        wrapper.register(MessagePetrificationCure::class.java, Side.SERVER)
        wrapper.register(MessagePetrificationDamage::class.java, Side.SERVER)
        wrapper.register(MessageSyncTalismanContainerSlot::class.java, Side.SERVER)
    }
}
