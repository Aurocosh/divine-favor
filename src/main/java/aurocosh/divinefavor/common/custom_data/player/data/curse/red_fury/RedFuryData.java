package aurocosh.divinefavor.common.custom_data.player.data.curse.red_fury;

import net.minecraft.util.math.Vec3d;

public class RedFuryData {
    private Vec3d vector;

    public RedFuryData() {
        this.vector = Vec3d.ZERO;
    }

    public void setVector(Vec3d vector) {
        this.vector = vector;
    }

    public Vec3d getVector() {
        return vector;
    }
}
