package aurocosh.divinefavor.common.network.base.serialization.serializer_provider.registration;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Color3fSerializer;
import aurocosh.divinefavor.common.network.base.serialization.buf_serializers.Vec3dSerializer;
import aurocosh.divinefavor.common.network.base.serialization.interfaces.BufWriter;
import aurocosh.divinefavor.common.network.base.serialization.serializer_provider.BufSerializerProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.vecmath.Color3f;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class NormalBufSerializerRegistration {
    @SubscribeEvent
    public static void onRegisterSerializers(BufSerializerRegistryEvent event) {
        registerWriters();
        registerReaders();
    }

    private static void registerReaders() {
        BufSerializerProvider.registerReader(int.class, ByteBuf::readInt);
        BufSerializerProvider.registerReader(long.class, ByteBuf::readLong);
        BufSerializerProvider.registerReader(float.class, ByteBuf::readFloat);
        BufSerializerProvider.registerReader(double.class, ByteBuf::readDouble);
        BufSerializerProvider.registerReader(boolean.class, ByteBuf::readBoolean);

        BufSerializerProvider.registerReader(Integer.class, ByteBuf::readInt);
        BufSerializerProvider.registerReader(Long.class, ByteBuf::readLong);
        BufSerializerProvider.registerReader(Float.class, ByteBuf::readFloat);
        BufSerializerProvider.registerReader(Double.class, ByteBuf::readDouble);
        BufSerializerProvider.registerReader(Boolean.class, ByteBuf::readBoolean);

        BufSerializerProvider.registerReader(String.class, ByteBufUtils::readUTF8String);
        BufSerializerProvider.registerReader(NBTTagCompound.class, ByteBufUtils::readTag);
        BufSerializerProvider.registerReader(ItemStack.class, ByteBufUtils::readItemStack);

        BufSerializerProvider.registerReader(char.class, ByteBuf::readChar);
        BufSerializerProvider.registerReader(byte.class, ByteBuf::readByte);
        BufSerializerProvider.registerReader(short.class, ByteBuf::readShort);

        BufSerializerProvider.registerReader(Character.class, ByteBuf::readChar);
        BufSerializerProvider.registerReader(Byte.class, ByteBuf::readByte);
        BufSerializerProvider.registerReader(Short.class, ByteBuf::readShort);

        BufSerializerProvider.registerReader(BlockPos.class, (buf) -> BlockPos.fromLong(buf.readLong()));

        BufSerializerProvider.registerReader(Vec3d.class, Vec3dSerializer::readVec3d);
        BufSerializerProvider.registerReader(Color3f.class, Color3fSerializer::readColor3f);
    }

    private static void registerWriters() {
        BufSerializerProvider.registerWriter(int.class, ByteBuf::writeInt);
        BufSerializerProvider.registerWriter(long.class, ByteBuf::writeLong);
        BufSerializerProvider.registerWriter(float.class, ByteBuf::writeFloat);
        BufSerializerProvider.registerWriter(double.class, ByteBuf::writeDouble);
        BufSerializerProvider.registerWriter(boolean.class, ByteBuf::writeBoolean);

        BufSerializerProvider.registerWriter(Integer.class, ByteBuf::writeInt);
        BufSerializerProvider.registerWriter(Long.class, ByteBuf::writeLong);
        BufSerializerProvider.registerWriter(Float.class, ByteBuf::writeFloat);
        BufSerializerProvider.registerWriter(Double.class, ByteBuf::writeDouble);
        BufSerializerProvider.registerWriter(Boolean.class, ByteBuf::writeBoolean);

        BufSerializerProvider.registerWriter(String.class, ByteBufUtils::writeUTF8String);
        BufSerializerProvider.registerWriter(NBTTagCompound.class, ByteBufUtils::writeTag);
        BufSerializerProvider.registerWriter(ItemStack.class, ByteBufUtils::writeItemStack);

        BufSerializerProvider.registerWriter(char.class, (BufWriter<Character>) ByteBuf::writeChar);
        BufSerializerProvider.registerWriter(byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        BufSerializerProvider.registerWriter(short.class, (BufWriter<Short>) ByteBuf::writeByte);

        BufSerializerProvider.registerWriter(Character.class, (BufWriter<Character>) ByteBuf::writeChar);
        BufSerializerProvider.registerWriter(Byte.class, (BufWriter<Byte>) ByteBuf::writeByte);
        BufSerializerProvider.registerWriter(Short.class, (BufWriter<Short>) ByteBuf::writeByte);

        BufSerializerProvider.registerWriter(BlockPos.class, (buf, pos) -> buf.writeLong(pos.toLong()));

        BufSerializerProvider.registerWriter(Vec3d.class, Vec3dSerializer::writeVec3d);
        BufSerializerProvider.registerWriter(Color3f.class, Color3fSerializer::writeColor3f);
    }
}
