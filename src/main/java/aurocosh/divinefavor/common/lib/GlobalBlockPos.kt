package aurocosh.divinefavor.common.lib

import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

data class GlobalBlockPos(val pos: BlockPos, val dimensionId: Int) {

    constructor(x: Int, y: Int, z: Int, dimensionId: Int) : this(BlockPos(x, y, z), dimensionId) {}

    constructor(x: Double, y: Double, z: Double, dimensionId: Int) : this(BlockPos(x, y, z), dimensionId) {}

    constructor(source: Entity) : this(BlockPos(source.posX, source.posY, source.posZ), source.dimension) {}

    constructor(vec: Vec3d, dimensionId: Int) : this(BlockPos(vec.x, vec.y, vec.z), dimensionId) {}

    constructor(source: Vec3i, dimensionId: Int) : this(BlockPos(source.x, source.y, source.z), dimensionId) {}
}
