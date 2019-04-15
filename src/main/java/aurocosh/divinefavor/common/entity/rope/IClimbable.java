package aurocosh.divinefavor.common.entity.rope;

import net.minecraft.entity.Entity;

public interface IClimbable {
    float getClimbingSpeed();
    boolean canClimb(Entity entityIn);
}
