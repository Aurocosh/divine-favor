package aurocosh.divinefavor.common.player_data.interaction_handler;

import aurocosh.divinefavor.common.lib.math.Vector3i;

import java.util.List;

public interface IInteractionHandler {
    void recordLastClickedPosition(Vector3i pos);

    boolean wasPositionClicked(Vector3i pos);

    List<Vector3i> getLastClickedPositions();

    void setLastClickedPositions(List<Vector3i> positions);
}
