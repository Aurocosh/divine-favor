package aurocosh.divinefavor.common.custom_data.player.data.interaction_handler;

import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class InteractionData {
    private static final int MAX_BLOCKS_REMEMBERED = 8;
    private static final LinkedList<BlockPos> CLICKED_BLOCKS = new LinkedList<>();

    public void recordLastClickedPosition(BlockPos pos) {
        if (CLICKED_BLOCKS.contains(pos))
            return;
        CLICKED_BLOCKS.add(pos);
        if (CLICKED_BLOCKS.size() > MAX_BLOCKS_REMEMBERED)
            CLICKED_BLOCKS.remove();
    }

    public boolean wasPositionClicked(BlockPos pos) {
        return CLICKED_BLOCKS.contains(pos);
    }

    public List<BlockPos> getLastClickedPositions() {
        return Collections.unmodifiableList(CLICKED_BLOCKS);
    }

    public void setLastClickedPositions(List<BlockPos> positions) {
        CLICKED_BLOCKS.clear();
        int posToAdd = Math.min(positions.size(), MAX_BLOCKS_REMEMBERED);
        for (int i = 0; i < posToAdd; i++)
            CLICKED_BLOCKS.add(positions.get(i));
    }
}
