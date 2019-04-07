package aurocosh.divinefavor.common.network.base;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class NetworkAutoMessage<REQ extends NetworkAutoMessage> implements IMessage, IMessageHandler<REQ, IMessage> {
    private static final Map<Class, BufWriter> writers = new HashMap<>();
    private static final Map<Class, BufReader> readers = new HashMap<>();

    private static final HashMap<Class, Field[]> mutableFields = new HashMap<>();

    static {
        writers.put(byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        writers.put(short.class, (BufWriter<Short>) ByteBuf::writeShort);
        writers.put(int.class, (BufWriter<Integer>) ByteBuf::writeInt);
        writers.put(long.class, (BufWriter<Long>) ByteBuf::writeLong);
        writers.put(float.class, (BufWriter<Float>) ByteBuf::writeFloat);
        writers.put(double.class, (BufWriter<Double>) ByteBuf::writeDouble);
        writers.put(boolean.class, (BufWriter<Boolean>) ByteBuf::writeBoolean);
        writers.put(char.class, (BufWriter<Character>) ByteBuf::writeChar);
        writers.put(String.class, (BufWriter<String>) ByteBufUtils::writeUTF8String);
        writers.put(NBTTagCompound.class, (BufWriter<NBTTagCompound>) ByteBufUtils::writeTag);
        writers.put(ItemStack.class, (BufWriter<ItemStack>) ByteBufUtils::writeItemStack);
        writers.put(BlockPos.class, (BufWriter<BlockPos>) (ByteBuf buf, BlockPos pos) -> buf.writeLong(pos.toLong()));
        writers.put(Vec3d.class, (BufWriter<Vec3d>) NetworkAutoMessage::writeVec3d);
    }

    static {
        readers.put(byte.class, (BufReader<Byte>) ByteBuf::readByte);
        readers.put(short.class, (BufReader<Short>) ByteBuf::readShort);
        readers.put(int.class, (BufReader<Integer>) ByteBuf::readInt);
        readers.put(long.class, (BufReader<Long>) ByteBuf::readLong);
        readers.put(float.class, (BufReader<Float>) ByteBuf::readFloat);
        readers.put(double.class, (BufReader<Double>) ByteBuf::readDouble);
        readers.put(boolean.class, (BufReader<Boolean>) ByteBuf::readBoolean);
        readers.put(char.class, (BufReader<Character>) ByteBuf::readChar);
        readers.put(String.class, (BufReader<String>) ByteBufUtils::readUTF8String);
        readers.put(NBTTagCompound.class, (BufReader<NBTTagCompound>) ByteBufUtils::readTag);
        readers.put(ItemStack.class, (BufReader<ItemStack>) ByteBufUtils::readItemStack);
        readers.put(BlockPos.class, (BufReader<BlockPos>) (ByteBuf buf) -> BlockPos.fromLong(buf.readLong()));
        readers.put(Vec3d.class, (BufReader<Vec3d>) NetworkAutoMessage::readVec3d);
    }

    private static Field[] getClassFields(Class<?> clazz) {
        Field[] fields = clazz.getFields();
        List<Field> mutableFields = new ArrayList<>();
        for (Field field : fields)
            if (isFieldMutable(field))
                mutableFields.add(field);
        mutableFields.sort(Comparator.comparing(Field::getName));

        Field[] mutableArray = new Field[mutableFields.size()];
        mutableArray = mutableFields.toArray(mutableArray);
        return mutableArray;
    }

    private static boolean isFieldMutable(Field field) {
        int mods = field.getModifiers();
        return !Modifier.isFinal(mods) && !Modifier.isStatic(mods) && !Modifier.isTransient(mods);
    }

    public IMessage handleMessage(MessageContext context) {
        return null;
    }

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] fields = mutableFields.computeIfAbsent(clazz, NetworkAutoMessage::getClassFields);
            for (Field field : fields) {
                Class<?> type = field.getType();
                BufReader reader = readers.get(type);
                field.set(this, reader.read(buf));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        try {
            Class<?> clazz = getClass();
            Field[] fields = mutableFields.computeIfAbsent(clazz, NetworkAutoMessage::getClassFields);
            for (Field field : fields) {
                Class<?> type = field.getType();
                BufWriter writer = writers.get(type);
                writer.write(buf, field.get(this));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    @FunctionalInterface
    public interface BufWriter<T> {
        void write(ByteBuf buf, T value);
    }

    @FunctionalInterface
    public interface BufReader<T> {
        T read(ByteBuf buf);
    }

    private static void writeVec3d(ByteBuf buf, Vec3d value) {
        buf.writeDouble(value.x);
        buf.writeDouble(value.y);
        buf.writeDouble(value.z);
    }

    private static Vec3d readVec3d(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new Vec3d(x, y, z);
    }
}
