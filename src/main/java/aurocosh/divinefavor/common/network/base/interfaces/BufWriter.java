package aurocosh.divinefavor.common.network.base.interfaces;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface BufWriter<T> {
    void write(ByteBuf buf, T value);
}
