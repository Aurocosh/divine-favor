package aurocosh.divinefavor.common.custom_data.player.data.escape_plan;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;

// The default implementation of the capability. Holds all the logic.
public class EscapePlanData {
    private GlobalBlockPos pos;

    public EscapePlanData() {
        pos = new GlobalBlockPos(0, 0, 0, 1);
    }

    public GlobalBlockPos getGlobalPosition() {
        return pos;
    }

    public void setGlobalPosition(GlobalBlockPos pos) {
        this.pos = pos;
    }
}
