package aurocosh.divinefavor.common.player_data.escape_plan;

import net.minecraft.util.math.BlockPos;

// The default implementation of the capability. Holds all the logic.
public class DefaultEscapePlanHandler implements IEscapePlanHandler {
    private BlockPos pos;
    private int dimensionId;

    public DefaultEscapePlanHandler() {
        this.pos = new BlockPos(0,0,0);
        this.dimensionId = 1;
    }

    @Override
    public BlockPos getPosition() {
        return pos;
    }

    @Override
    public void setPosition(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public int getDimension() {
        return dimensionId;
    }

    @Override
    public void setDimension(int dimensionId) {
        this.dimensionId = dimensionId;
    }
}
