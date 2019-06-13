package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.network.AutoNetworkWrapper
import aurocosh.divinefavor.DivineFavor
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
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

object NetworkHandler {
    val autoWrapper = AutoNetworkWrapper(DivineFavor.MOD_ID)

    val wrapper: SimpleNetworkWrapper
        get() = autoWrapper.wrapper

    fun init() {
        // Client messages
        autoWrapper.register(MessageConfigOreBlocks::class.java, Side.CLIENT)

        autoWrapper.register(MessageParticlesHighlightEntities::class.java, Side.CLIENT)
        autoWrapper.register(MessageParticlesHighlightFloodFill::class.java, Side.CLIENT)
        autoWrapper.register(MessageParticlesHighlightInSphere::class.java, Side.CLIENT)
        autoWrapper.register(MessageParticlesWave::class.java, Side.CLIENT)
        autoWrapper.register(MessageParticlesWinterBreath::class.java, Side.CLIENT)

        autoWrapper.register(MessageSyncAllSpiritData::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncFavorInfinite::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncContracts::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncFavor::class.java, Side.CLIENT)

        autoWrapper.register(MessagePetrificationReset::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncEvilEye::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncFireImmunity::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncFlyingCapability::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncFury::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncGrudge::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncPotionCharge::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncRedFury::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncWindLeash::class.java, Side.CLIENT)

        autoWrapper.register(MessageSpiritBecameActive::class.java, Side.CLIENT)
        autoWrapper.register(MessageSpiritBecameInactive::class.java, Side.CLIENT)

        // Server messages
        autoWrapper.register(MessagePetrificationCure::class.java, Side.SERVER)
        autoWrapper.register(MessagePetrificationDamage::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanContainerSlot::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyBool::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyInt::class.java, Side.SERVER)
    }
}
