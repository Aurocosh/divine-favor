package aurocosh.divinefavor.common.custom_data.player.pearl_crumbs;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;

import java.util.List;

public interface IPearlCrumbsHandler {
    GlobalBlockPos popGlobalPosition();
    void pushGlobalPosition(GlobalBlockPos pos);

    boolean hasPositions();

    List<GlobalBlockPos> getAllPositions();
    void setAllPositions(List<GlobalBlockPos> positions);
}
