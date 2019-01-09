package aurocosh.divinefavor.common.player_data.escape_plan;

import net.minecraft.util.math.BlockPos;

public interface IEscapePlanHandler {
    BlockPos getPosition();
    void setPosition(BlockPos pos);

    int getDimension();
    void setDimension(int dimensionId);
}
