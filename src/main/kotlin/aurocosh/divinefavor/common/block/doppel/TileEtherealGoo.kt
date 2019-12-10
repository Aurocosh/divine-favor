package aurocosh.divinefavor.common.block.doppel

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.block.soulbound_lectern.NbtPropertyIBlockState
import aurocosh.divinefavor.common.lib.extensions.getOrNull
import aurocosh.divinefavor.common.lib.extensions.set
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

/**
 * This class was adapted from code written by Direwolf20 for the BuildingGadgets mod: https://github.com/Direwolf20-MC/BuildingGadgets
 * BuildingGadgets is Open Source and distributed under MIT
 */

class TileEtherealGoo : TileEntity() {
    var blockState: IBlockState? = null
        get() {
            return if (field == null || field === Blocks.AIR.defaultState) null else field
        }
        set(value) {
            field = when {
                value == null -> value
                value.block === ModBlocks.ethereal_goo -> null
                else -> value
            }
            markDirtyClient()
        }

    var actualBlockState: IBlockState? = null
        get() {
            return if (field == null || field === Blocks.AIR.defaultState) null else field
        }
        set(value) {
            field = when {
                value == null -> value
                value.block === ModBlocks.ethereal_goo -> null
                else -> value
            }
            markDirtyClient()
        }

    private fun markDirtyClient() {
        markDirty()
        if (world != null) {
            val state = world.getBlockState(pos)
            world.notifyBlockUpdate(pos, state, state, 3)
        }
    }

    override fun getUpdateTag(): NBTTagCompound {
        val updateTag = super.getUpdateTag()
        writeToNBT(updateTag)
        return updateTag
    }

    override fun getUpdatePacket(): SPacketUpdateTileEntity? {
        val nbtTag = NBTTagCompound()
        writeToNBT(nbtTag)
        return SPacketUpdateTileEntity(pos, 1, nbtTag)
    }

    override fun onDataPacket(net: NetworkManager, packet: SPacketUpdateTileEntity) {
        val oldMimicBlock = blockState
        val tagCompound = packet.nbtCompound
        super.onDataPacket(net, packet)
        readFromNBT(tagCompound)
        if (world.isRemote) {
            // If needed send a render update.
            if (blockState !== oldMimicBlock) {
                world.markBlockRangeForRenderUpdate(pos, pos)
            }
        }
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        this.blockState = compound.getOrNull(stateProperty)
        this.actualBlockState = compound.getOrNull(stateProperty)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        super.writeToNBT(compound)
        val blockState = blockState
        val actualBlockState = actualBlockState
        if (blockState != null) {
            compound.set(stateProperty, blockState)
            if (actualBlockState != null)
                compound.set(actualStateProperty, actualBlockState)
        }
        return compound
    }

    companion object {
        private val stateProperty = NbtPropertyIBlockState("blockState", Blocks.AIR.defaultState, listOf("tag_blockState"))
        private val actualStateProperty = NbtPropertyIBlockState("actualBlockState", Blocks.AIR.defaultState, listOf("tag_actualBlockState"))
    }
}
