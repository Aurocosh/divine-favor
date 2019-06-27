package aurocosh.divinefavor.common.lib.extensions

import aurocosh.divinefavor.common.custom_data.global.GlobalSaveDataId
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.storage.WorldSavedData

fun World.getBlock(pos: BlockPos): Block {
    return this.getBlockState(pos).block
}

fun World.hasTileEntity(pos: BlockPos): Boolean {
    return this.getTileEntity(pos) != null
}

fun World.getMaterial(pos: BlockPos): Material {
    return this.getBlockState(pos).material
}

fun World.isWood(pos: BlockPos): Boolean {
    return this.getBlockState(pos).block.isWood(this, pos)
}

fun World.isLeaves(pos: BlockPos): Boolean {
    val blockState = this.getBlockState(pos)
    val block = blockState.block
    return block.isLeaves(blockState, this, pos)
}

fun World.isAirOrReplacable(pos: BlockPos): Boolean {
    return this.isAirBlock(pos) || this.getBlockState(pos).block.isReplaceable(this, pos)
}

inline operator fun <reified T : WorldSavedData, reified K : GlobalSaveDataId<T>> World.get(globalDataId: K): T {
    val storage = this.mapStorage
            ?: throw IllegalStateException("World#getMapStorage returned null. The following WorldSave is inaccessible: $globalDataId.name")
    val instance = storage.getOrLoadData(globalDataId.clazz, globalDataId.name)
    if (instance != null)
        return instance as T

    val newInstance = globalDataId.makeNewInstance()
    storage.setData(globalDataId.name, newInstance)
    return newInstance
}