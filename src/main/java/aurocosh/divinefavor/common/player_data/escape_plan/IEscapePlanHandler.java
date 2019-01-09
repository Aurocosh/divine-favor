package aurocosh.divinefavor.common.player_data.escape_plan;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;

public interface IEscapePlanHandler {
    GlobalBlockPos getGlobalPosition();
    void setGlobalPosition(GlobalBlockPos pos);
}
