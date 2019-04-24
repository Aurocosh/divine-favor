package aurocosh.divinefavor.common.network.base.serialization.class_serializers.registering;

import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Color3fSerializer;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Vec3dSerializer;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.vecmath.Color3f;

import static aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider.registerReader;
import static aurocosh.divinefavor.common.network.base.serialization.class_serializers.TypeBufSerializerProvider.registerWriter;

public class NormalSerializerRegistration {
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
}
