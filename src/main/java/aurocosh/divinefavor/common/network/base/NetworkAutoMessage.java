package aurocosh.divinefavor.common.network.base;

import aurocosh.divinefavor.common.network.base.serialization.class_serializers.ClassBufSerializer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;

public abstract class NetworkAutoMessage<REQ extends NetworkAutoMessage> implements IMessage, IMessageHandler<REQ, IMessage> {
    private static final HashMap<Class, ClassBufSerializer> serializers = new HashMap<>();

    @Override
    public final void fromBytes(ByteBuf buf) {
        Class clazz = getClass();
        ClassBufSerializer serializer = serializers.computeIfAbsent(clazz, ClassBufSerializer::new);
        serializer.fromBytes(this, buf);
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        Class clazz = getClass();
        ClassBufSerializer serializer = serializers.computeIfAbsent(clazz, ClassBufSerializer::new);
        serializer.toBytes(this, buf);
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    public abstract IMessage handleMessage(MessageContext context);
}
