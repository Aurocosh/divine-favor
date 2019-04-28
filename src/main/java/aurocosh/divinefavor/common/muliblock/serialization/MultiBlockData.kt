package aurocosh.divinefavor.common.muliblock.serialization

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.muliblock.MultiBlockPart
import com.google.gson.annotations.Expose
import net.minecraft.util.math.BlockPos
import java.util.*

class MultiBlockData {
    @Expose
    var symmetrical: Boolean = false
    @Expose
    var basePosition: BlockPos
    @Expose
    var controllerPosition: BlockPos
    @Expose
    var parts: List<MultiBlockPart>

    constructor() {
        symmetrical = true
        basePosition = BlockPosConstants.ZERO
        controllerPosition = BlockPosConstants.ZERO
        parts = ArrayList()
    }

    constructor(symmetrical: Boolean, basePosition: BlockPos, controllerPosition: BlockPos, parts: List<MultiBlockPart>) {
        this.symmetrical = symmetrical
        this.basePosition = basePosition
        this.controllerPosition = controllerPosition
        this.parts = parts
    }
}
