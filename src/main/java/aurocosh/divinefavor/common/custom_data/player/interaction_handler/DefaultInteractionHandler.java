package aurocosh.divinefavor.common.custom_data.player.interaction_handler;

import aurocosh.divinefavor.common.lib.math.Vector3i;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class DefaultInteractionHandler implements IInteractionHandler {
    private static int MAX_BLOCKS_REMEBERED = 8;
    private static LinkedList<Vector3i> clickedBlocks = new LinkedList<>();

    @Override
    public void recordLastClickedPosition(Vector3i pos) {
        if(clickedBlocks.contains(pos))
            return;
        clickedBlocks.add(pos);
        if(clickedBlocks.size() > MAX_BLOCKS_REMEBERED)
            clickedBlocks.remove();
    }

    @Override
    public boolean wasPositionClicked(Vector3i pos) {
        return clickedBlocks.contains(pos);
    }

    @Override
    public List<Vector3i> getLastClickedPositions() {
        return Collections.unmodifiableList(clickedBlocks);
    }

    @Override
    public void setLastClickedPositions(List<Vector3i> positions) {
        clickedBlocks.clear();
        int posToAdd = Math.min(positions.size(),MAX_BLOCKS_REMEBERED);
        for (int i = 0; i < posToAdd; i++)
            clickedBlocks.add(positions.get(i));
    }
}
