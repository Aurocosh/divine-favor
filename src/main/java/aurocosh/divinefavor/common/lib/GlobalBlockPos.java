package aurocosh.divinefavor.common.lib;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.Objects;

public class GlobalBlockPos {
    public final BlockPos pos;
    public final int dimensionId;

    public GlobalBlockPos(int x, int y, int z, int dimensionId) {
        this(new BlockPos(x, y, z), dimensionId);
    }

    public GlobalBlockPos(double x, double y, double z, int dimensionId) {
        this(new BlockPos(x, y, z), dimensionId);
    }

    public GlobalBlockPos(Entity source) {
        this(new BlockPos(source.posX, source.posY, source.posZ), source.dimension);
    }

    public GlobalBlockPos(Vec3d vec, int dimensionId) {
        this(new BlockPos(vec.x, vec.y, vec.z), dimensionId);
    }

    public GlobalBlockPos(Vec3i source, int dimensionId) {
        this(new BlockPos(source.getX(), source.getY(), source.getZ()), dimensionId);
    }

    public GlobalBlockPos(BlockPos pos, int dimensionId) {
        this.pos = pos;
        this.dimensionId = dimensionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof GlobalBlockPos))
            return false;
        if (o == this)
            return true;

        GlobalBlockPos other = (GlobalBlockPos) o;
        return pos.equals(other.pos) && dimensionId == other.dimensionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, dimensionId);
    }
}
