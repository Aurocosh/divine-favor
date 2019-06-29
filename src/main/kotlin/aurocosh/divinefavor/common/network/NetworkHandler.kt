package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.network.AutoNetworkWrapper
import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.message.client.MessageSendBlockTemplateClient
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameActive
import aurocosh.divinefavor.common.network.message.client.activity.MessageSpiritBecameInactive
import aurocosh.divinefavor.common.network.message.client.config.MessageConfigOreBlocks
import aurocosh.divinefavor.common.network.message.client.particles.*
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncAllSpiritData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncContracts
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavorInfinite
import aurocosh.divinefavor.common.network.message.client.syncing.*
import aurocosh.divinefavor.common.network.message.sever.MessageRequestTemplate
import aurocosh.divinefavor.common.network.message.sever.MessageSendBlockTemplateServer
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset
import aurocosh.divinefavor.common.network.message.sever.stack_properties.*
import aurocosh.divinefavor.common.network.message.sever.syncing.*
import aurocosh.divinefavor.common.network.message.sever.talisman_properties.*
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

object NetworkHandler {
    private val autoWrapper = AutoNetworkWrapper(DivineFavor.MOD_ID)

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

        autoWrapper.register(MessageSendBlockTemplateClient::class.java, Side.CLIENT)
        autoWrapper.register(MessageSyncTemplateClient::class.java, Side.CLIENT)

        // Server messages
        autoWrapper.register(MessagePetrificationCure::class.java, Side.SERVER)
        autoWrapper.register(MessagePetrificationDamage::class.java, Side.SERVER)

        autoWrapper.register(MessageSyncMemoryPouchDropName::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncMemoryPouchSlot::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanContainerSlot::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyIndex::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTemplateServer::class.java, Side.SERVER)

        autoWrapper.register(MessageSyncPropertyBlockPos::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyBool::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyEnumFacing::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyFloat::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyIBlockState::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyInt::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyUUID::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyEnum::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncPropertyString::class.java, Side.SERVER)

        autoWrapper.register(MessageSyncTalismanPropertyBlockPos::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyBool::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyEnumFacing::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyFloat::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyIBlockState::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyInt::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyUUID::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyEnum::class.java, Side.SERVER)
        autoWrapper.register(MessageSyncTalismanPropertyString::class.java, Side.SERVER)

        autoWrapper.register(MessageRequestTemplate::class.java, Side.SERVER)
        autoWrapper.register(MessageSendBlockTemplateServer::class.java, Side.SERVER)
    }
}
