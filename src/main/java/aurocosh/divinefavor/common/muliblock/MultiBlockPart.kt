package aurocosh.divinefavor.common.muliblock

import aurocosh.divinefavor.common.muliblock.validators.StateValidator
import com.google.gson.annotations.Expose
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class MultiBlockPart(validator: StateValidator, positions: List<BlockPos>) {
    @field:Expose
    val validator = validator
    @Expose
    val positions: List<BlockPos> = Collections.unmodifiableList(ArrayList(positions))

    fun isAllValid(world: World, controller: BlockPos): Boolean {
        for (position in positions) {
            val blockPosition = position.add(controller)
            val state = world.getBlockState(blockPosition)
            if (!validator.isValid(state))
                return false
        }
        return true
    }
}
