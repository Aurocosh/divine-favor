package aurocosh.divinefavor.common.network

import aurocosh.autonetworklib.network.serialization.serializer_provider.BufSerializerProvider
import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.serializers.IBlockStateSerializer
import aurocosh.divinefavor.common.network.serializers.UUIDSerializer
import net.minecraft.block.state.IBlockState
import java.util.*

object ModBufSerializers {
    fun preInit() {
        BufSerializerProvider.registerWriter(IBlockState::class.java, IBlockStateSerializer()::write)
        BufSerializerProvider.registerWriter(UUID::class.java, UUIDSerializer()::write)

        BufSerializerProvider.registerReader(IBlockState::class.java, IBlockStateSerializer()::read)
        BufSerializerProvider.registerReader(UUID::class.java, UUIDSerializer()::read)

        DivineFavor.logger.info("Registered custom serializers")
    }
}
