package aurocosh.divinefavor.common.network.serializers

import io.netty.buffer.ByteBuf
import java.util.*

class UUIDSerializer {
    fun write(buf: ByteBuf, value: UUID) {
        buf.writeLong(value.leastSignificantBits)
        buf.writeLong(value.mostSignificantBits)
    }

    fun read(buf: ByteBuf): UUID {
        val leastSigBits = buf.readLong()
        val mostSigBits = buf.readLong()
        return UUID(mostSigBits, leastSigBits)
    }
}
