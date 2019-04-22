package aurocosh.divinefavor.common.network.base.interfaces;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface BufReader<T> {
    T read(ByteBuf buf);
}
