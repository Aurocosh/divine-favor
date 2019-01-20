package aurocosh.divinefavor.common.custom_data.player.escape_plan;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;

// The default implementation of the capability. Holds all the logic.
public class DefaultEscapePlanHandler implements IEscapePlanHandler {
    private GlobalBlockPos pos;

    public DefaultEscapePlanHandler() {
        pos = new GlobalBlockPos(0, 0, 0, 1);
    }

    @Override
    public GlobalBlockPos getGlobalPosition() {
        return pos;
    }

    @Override
    public void setGlobalPosition(GlobalBlockPos pos) {
        this.pos = pos;
    }
}
