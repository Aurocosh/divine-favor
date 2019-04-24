package aurocosh.divinefavor.common.network.base.serialization.class_serializers;

import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Color3fSerializer;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.EnumSerializer;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Vec3dSerializer;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.array_list.CollectionSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.generic.hash_set.MapSerializerProvider;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufReader;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.GenericSerializerProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.vecmath.Color3f;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class TypeBufSerializerProvider {
    private static final Map<Class, BufWriter> writers = new HashMap<>();
    private static final Map<Class, BufReader> readers = new HashMap<>();

    private static final Map<Class, EnumSerializer> enumSerializers = new HashMap<>();
    private static final Map<Class, GenericSerializerProvider> providers = new HashMap<>();

    static {
        registerWriter(int.class, ByteBuf::writeInt);
        registerWriter(long.class, ByteBuf::writeLong);
        registerWriter(float.class, ByteBuf::writeFloat);
        registerWriter(double.class, ByteBuf::writeDouble);
        registerWriter(boolean.class, ByteBuf::writeBoolean);

        registerWriter(Integer.class, ByteBuf::writeInt);
        registerWriter(Long.class, ByteBuf::writeLong);
        registerWriter(Float.class, ByteBuf::writeFloat);
        registerWriter(Double.class, ByteBuf::writeDouble);
        registerWriter(Boolean.class, ByteBuf::writeBoolean);

        registerWriter(String.class, ByteBufUtils::writeUTF8String);
        registerWriter(NBTTagCompound.class, ByteBufUtils::writeTag);
        registerWriter(ItemStack.class, ByteBufUtils::writeItemStack);

        registerWriter(char.class, (BufWriter<Character>) ByteBuf::writeChar);
        registerWriter(byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        registerWriter(short.class, (BufWriter<Short>) ByteBuf::writeByte);

        registerWriter(Character.class, (BufWriter<Character>) ByteBuf::writeChar);
        registerWriter(Byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        registerWriter(Short.class, (BufWriter<Short>) ByteBuf::writeByte);

        registerWriter(BlockPos.class, (buf, pos) -> buf.writeLong(pos.toLong()));

        registerWriter(Vec3d.class, Vec3dSerializer::writeVec3d);
        registerWriter(Color3f.class, Color3fSerializer::writeColor3f);
    }

    static {
        registerReader(int.class, ByteBuf::readInt);
        registerReader(long.class, ByteBuf::readLong);
        registerReader(float.class, ByteBuf::readFloat);
        registerReader(double.class, ByteBuf::readDouble);
        registerReader(boolean.class, ByteBuf::readBoolean);

        registerReader(Integer.class, ByteBuf::readInt);
        registerReader(Long.class, ByteBuf::readLong);
        registerReader(Float.class, ByteBuf::readFloat);
        registerReader(Double.class, ByteBuf::readDouble);
        registerReader(Boolean.class, ByteBuf::readBoolean);

        registerReader(String.class, ByteBufUtils::readUTF8String);
        registerReader(NBTTagCompound.class, ByteBufUtils::readTag);
        registerReader(ItemStack.class, ByteBufUtils::readItemStack);

        registerReader(char.class, ByteBuf::readChar);
        registerReader(byte.class, ByteBuf::readByte);
        registerReader(short.class, ByteBuf::readShort);

        registerReader(Character.class, ByteBuf::readChar);
        registerReader(Byte.class, ByteBuf::readByte);
        registerReader(Short.class, ByteBuf::readShort);

        registerReader(BlockPos.class, (buf) -> BlockPos.fromLong(buf.readLong()));

        registerReader(Vec3d.class, Vec3dSerializer::readVec3d);
        registerReader(Color3f.class, Color3fSerializer::readColor3f);
    }

    static {
        registerSerializerProvider(List.class, new CollectionSerializerProvider<>(ArrayList::new));
        registerSerializerProvider(ArrayList.class, new CollectionSerializerProvider<>(ArrayList::new));
        registerSerializerProvider(Stack.class, new CollectionSerializerProvider<>(capacity -> new Stack()));
        registerSerializerProvider(Vector.class, new CollectionSerializerProvider<>(capacity -> new Vector()));
        registerSerializerProvider(LinkedList.class, new CollectionSerializerProvider<>(capacity -> new LinkedList()));

        registerSerializerProvider(Set.class, new CollectionSerializerProvider<>(HashSet::new));
        registerSerializerProvider(HashSet.class, new CollectionSerializerProvider<>(HashSet::new));

        registerSerializerProvider(Map.class, new MapSerializerProvider<>(HashMap::new));
        registerSerializerProvider(HashMap.class, new MapSerializerProvider<>(HashMap::new));
    }

    public static <T> void registerWriter(Class<T> clazz, BufWriter<T> writer) {
        writers.put(clazz, writer);
    }

    public static <T> void registerReader(Class<T> clazz, BufReader<T> reader) {
        readers.put(clazz, reader);
    }

    public static <T> void registerSerializerProvider(Class<T> clazz, GenericSerializerProvider<T> provider) {
        providers.put(clazz, provider);
    }

    public static BufReader getReader(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return providers.get(rawType).getReader(parameterizedType);
        }
        else if(type instanceof Class && ((Class<?>)type).isEnum()){
            Class enumClazz = (Class) type;
            EnumSerializer serializer = enumSerializers.computeIfAbsent(enumClazz, EnumSerializer::new);
            return serializer;
        }
        //noinspection SuspiciousMethodCalls
        return readers.get(type);
    }

    public static BufWriter getWriter(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class rawType = (Class) parameterizedType.getRawType();
            return providers.get(rawType).getWriter(parameterizedType);
        }
        else if(type instanceof Class && ((Class<?>)type).isEnum()){
            Class enumClazz = (Class) type;
            EnumSerializer serializer = enumSerializers.computeIfAbsent(enumClazz, EnumSerializer::new);
            return serializer;
        }
        //noinspection SuspiciousMethodCalls
        return writers.get(type);
    }

    public static BufReader[] getReaders(Type[] fields) {
        BufReader[] readers = new BufReader[fields.length];
        for (int i = 0; i < fields.length; i++)
            readers[i] = getReader(fields[i]);
        return readers;
    }

    public static BufWriter[] getWriters(Type[] fields) {
        BufWriter[] writers = new BufWriter[fields.length];
        for (int i = 0; i < fields.length; i++)
            writers[i] = getWriter(fields[i]);
        return writers;
    }
}
