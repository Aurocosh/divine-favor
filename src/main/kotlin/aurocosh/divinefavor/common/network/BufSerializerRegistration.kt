package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.AutoNetworkLib
import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider
import aurocosh.autonetworklib.network.serialization.serializer_provider.registration.BufSerializerRegistryEvent
import aurocosh.divinefavor.common.network.serializers.IBlockStateSerializer
import net.minecraft.block.state.IBlockState
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = AutoNetworkLib.MOD_ID)
object BufSerializerRegistration {
    @SubscribeEvent
    fun onRegisterSerializers(event: BufSerializerRegistryEvent) {
        BufSerializerProvider.registerWriter(IBlockState::class.java, IBlockStateSerializer()::write)
        BufSerializerProvider.registerReader(IBlockState::class.java, IBlockStateSerializer()::read)
    }
}
