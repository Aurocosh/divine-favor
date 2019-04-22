package aurocosh.divinefavor.common.network.base.serialization;

import aurocosh.divinefavor.common.network.base.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.generic.ArrayListReader;
import aurocosh.divinefavor.common.network.base.serialization.generic.ArrayListWriter;
import aurocosh.divinefavor.common.network.base.serialization.serializers.Color3fSerializer;
import aurocosh.divinefavor.common.network.base.serialization.serializers.Vec3dSerializer;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.vecmath.Color3f;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldByteBufSerializers {
    private static final Map<Class, BufWriter> writers = new HashMap<>();
    private static final Map<Class, BufReader> readers = new HashMap<>();

    private static final Map<Class, BufWriter> listWriters = new HashMap<>();
    private static final Map<Class, BufReader> listReaders = new HashMap<>();

    static {
        registerWriter(int.class, ByteBuf::writeInt);
        registerWriter(long.class, ByteBuf::writeLong);
        registerWriter(float.class, ByteBuf::writeFloat);
        registerWriter(double.class, ByteBuf::writeDouble);
        registerWriter(boolean.class, ByteBuf::writeBoolean);
        registerWriter(String.class, ByteBufUtils::writeUTF8String);
        registerWriter(NBTTagCompound.class, ByteBufUtils::writeTag);
        registerWriter(ItemStack.class, ByteBufUtils::writeItemStack);

        registerWriter(char.class, (BufWriter<Character>) ByteBuf::writeChar);
        registerWriter(byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        registerWriter(short.class, (BufWriter<Short>) ByteBuf::writeByte);
        registerWriter(BlockPos.class, (buf, pos) -> buf.writeLong(pos.toLong()));

        registerWriter(Vec3d.class, Vec3dSerializer::writeVec3d);
        registerWriter(Color3f.class, Color3fSerializer::writeColor3f);

        for (Map.Entry<Class, BufWriter> entry : writers.entrySet()) {
            ArrayListWriter arrayListWriter = new ArrayListWriter(entry.getValue());
            listWriters.put(entry.getKey(), (BufWriter<ArrayList>) arrayListWriter::write);
        }
    }

    static {
        registerReader(int.class, ByteBuf::readInt);
        registerReader(long.class, ByteBuf::readLong);
        registerReader(float.class, ByteBuf::readFloat);
        registerReader(double.class, ByteBuf::readDouble);
        registerReader(boolean.class, ByteBuf::readBoolean);
        registerReader(String.class, ByteBufUtils::readUTF8String);
        registerReader(NBTTagCompound.class, ByteBufUtils::readTag);
        registerReader(ItemStack.class, ByteBufUtils::readItemStack);

        registerReader(char.class, (BufReader<Character>) ByteBuf::readChar);
        registerReader(byte.class, (BufReader<Byte>) ByteBuf::readByte);
        registerReader(short.class, (BufReader<Short>) ByteBuf::readShort);
        registerReader(BlockPos.class, (ByteBuf buf) -> BlockPos.fromLong(buf.readLong()));

        registerReader(Vec3d.class, Vec3dSerializer::readVec3d);
        registerReader(Color3f.class, Color3fSerializer::readColor3f);

        for (Map.Entry<Class, BufReader> entry : readers.entrySet()) {
            ArrayListReader arrayListReader = new ArrayListReader(entry.getValue());
            listReaders.put(entry.getKey(), (BufReader<ArrayList>) arrayListReader::read);
        }
    }

    public static <T> void registerWriter(Class<T> clazz, BufWriter<T> writer) {
        writers.put(clazz, writer);
    }

    public static void registerReader(Class clazz, BufReader reader) {
        readers.put(clazz, reader);
    }

    public static BufWriter getWriter(Field field) {
        Class type = field.getType();
        if (type != ArrayList.class)
            return writers.get(type);
        Type actualType = getActualType(field);
        if (actualType != null)
            return listWriters.get(actualType);
        return null;
    }

    public static BufReader getReader(Field field) {
        Class type = field.getType();
        if (type != ArrayList.class)
            return readers.get(type);
        Type actualType = getActualType(field);
        if (actualType != null)
            return listReaders.get(actualType);
        return null;
    }

    private static Type getActualType(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericType;
            return type.getActualTypeArguments()[0];
        }
        return null;
    }

    public static BufReader[] getFieldReaders(Field[] fields) {
        BufReader[] readers = new BufReader[fields.length];
        for (int i = 0; i < fields.length; i++)
            readers[i] = getReader(fields[i]);
        return readers;
    }

    public static BufWriter[] getFieldWriters(Field[] fields) {
        BufWriter[] writers = new BufWriter[fields.length];
        for (int i = 0; i < fields.length; i++)
            writers[i] = getWriter(fields[i]);
        return writers;
    }
}
