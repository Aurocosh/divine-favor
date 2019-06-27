package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.AutoNetworkLib
import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider
import aurocosh.autonetworklib.network.serialization.serializer_provider.registration.BufSerializerRegistryEvent
import aurocosh.divinefavor.common.network.serializers.IBlockStateSerializer
import aurocosh.divinefavor.common.network.serializers.UUIDSerializer
import net.minecraft.block.state.IBlockState
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

@Mod.EventBusSubscriber(modid = AutoNetworkLib.MOD_ID)
object BufSerializerRegistration {
    @SubscribeEvent
    fun onRegisterSerializers(event: BufSerializerRegistryEvent) {
        BufSerializerProvider.registerWriter(IBlockState::class.java, IBlockStateSerializer()::write)
        BufSerializerProvider.registerWriter(UUID::class.java, UUIDSerializer()::write)

        BufSerializerProvider.registerReader(IBlockState::class.java, IBlockStateSerializer()::read)
        BufSerializerProvider.registerReader(UUID::class.java, UUIDSerializer()::read)
    }
}
