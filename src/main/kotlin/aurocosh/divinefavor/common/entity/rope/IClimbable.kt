package aurocosh.divinefavor.common.entity.rope

import net.minecraft.entity.Entity

interface IClimbable {
    fun getClimbingSpeed(): Float
    fun canClimb(entity: Entity): Boolean
}
