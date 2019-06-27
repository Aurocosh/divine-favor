package aurocosh.divinefavor.common.network.serializers

import aurocosh.divinefavor.common.lib.EmptyConst.emptyUUID
import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.ByteBufUtils
import java.util.*

class UUIDSerializer {
    fun write(buf: ByteBuf, value: UUID) {
        ByteBufUtils.writeUTF8String(buf, value.toString())
    }

    fun read(buf: ByteBuf): UUID {
        val string = ByteBufUtils.readUTF8String(buf)
        return UUID.fromString(string) ?: emptyUUID()
    }
}
