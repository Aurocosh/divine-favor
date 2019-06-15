package aurocosh.divinefavor.common.network.serializers

import io.netty.buffer.ByteBuf
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTUtil
import net.minecraftforge.fml.common.network.ByteBufUtils

class IBlockStateSerializer {
    fun write(buf: ByteBuf, value: IBlockState) {
        val tag = NBTTagCompound()
        NBTUtil.writeBlockState(tag, value)
        ByteBufUtils.writeTag(buf, tag)
    }

    fun read(buf: ByteBuf): IBlockState {
        val tag = ByteBufUtils.readTag(buf) as NBTTagCompound
        return NBTUtil.readBlockState(tag)
    }
}
