package aurocosh.divinefavor.common.block.base

import aurocosh.divinefavor.common.nbt_properties.NbtPropertySerializer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

abstract class ModTileEntity() : TileEntity() {
    protected val stateSerializer = NbtPropertySerializer()
    protected val updateSerializer = NbtPropertySerializer()

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        stateSerializer.readFromNbt(compound)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        stateSerializer.writeToNbt(compound)
        return compound
    }

    override fun getUpdateTag(): NBTTagCompound {
        val compound = super.getUpdateTag()
        updateSerializer.writeToNbt(compound)
        return compound
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        return SPacketUpdateTileEntity(pos, 1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, packet: SPacketUpdateTileEntity?) {
        if (!world.isRemote)
            return

        val compound = packet?.nbtCompound ?: return

        val oldState = getRenderStateKey()
        updateSerializer.readFromNbt(compound)
        val newState = getRenderStateKey()

        if (!(oldState contentEquals newState))
            world.markBlockRangeForRenderUpdate(pos, pos)
    }

    protected open fun getRenderStateKey(): Array<out Any> = emptyArray()
}