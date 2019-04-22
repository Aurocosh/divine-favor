package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.common.network.base.serialization.ClassByteBufSerializer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;

public abstract class NetworkAutoMessage<REQ extends NetworkAutoMessage> implements IMessage, IMessageHandler<REQ, IMessage> {
    private static final HashMap<Class, ClassByteBufSerializer> serializers = new HashMap<>();

    @Override
    public final void fromBytes(ByteBuf buf) {
        Class clazz = getClass();
        ClassByteBufSerializer serializer = serializers.computeIfAbsent(clazz, ClassByteBufSerializer::new);
        serializer.fromBytes(this, buf);
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        Class clazz = getClass();
        ClassByteBufSerializer serializer = serializers.computeIfAbsent(clazz, ClassByteBufSerializer::new);
        serializer.toBytes(this, buf);
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    public abstract IMessage handleMessage(MessageContext context);
}
